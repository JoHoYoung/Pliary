package com.example.myapp.controller.auth;

import com.example.myapp.context.user.ChangePassword;
import com.example.myapp.context.user.Signin;
import com.example.myapp.context.user.Signup;
import com.example.myapp.jwt.JwtServiceImpl;
import com.example.myapp.mapper.UserMapper;
import com.example.myapp.model.UserModel;
import com.example.myapp.util.AES256Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.web.bind.annotation.*;
import java.util.Random;

// 가입, 로그인, 이메일인증, 토큰 갱신, 토큰 인증..
@RestController
@RequestMapping(value = "/auth", consumes = MediaType.APPLICATION_JSON_VALUE)
public class User {

    private static final Logger LOG = LogManager.getLogger(User.class);

    @Autowired
    private JwtServiceImpl jwtService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AES256Util Encoder;
    @Autowired
    public JavaMailSender emailSender;

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public JSONObject Signup(@RequestBody Signup param)
    {
        JSONObject JSON = new JSONObject();
        String uid = param.getUid();
        String email=param.getEmail();
        UserModel user = userMapper.getUser(email);
        if(user != null){
            JSON.put("statusCode", 700);
            JSON.put("statusMsg", "Email exist");
            LOG.warn("Email exist");
            return JSON;
        }
        // 회원 가입시 랜덤 토큰 생성, 부여 - > 이메일 인증을 위함.
        char[] charaters = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','0','1','2','3','4','5','6','7','8','9'};
        StringBuffer sb = new StringBuffer();
        // 토큰 생성
        Random rn = new Random();
        for( int i = 0 ; i < 13 ; i++ ){
            sb.append( charaters[ rn.nextInt( charaters.length ) ] );
        }
        String token = sb.toString();
        // 이메일을 보냄
        MimeMessage msg=emailSender.createMimeMessage();
        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(msg, false, "UTF-8");
            String text = "<html><body><p>인증을 위해 아래 링크를 클릭해 주세요</p>" +
                    "<a href='http://15.164.169.58:8080/api/cert/?&token=" + token + "'>여기를 눌러 인증해주세요</a></body></html>";
            helper.setTo(email);
            helper.setSubject("Team Groot - 이메일 인증");
            helper.setText(text,true);
            emailSender.send(msg);
            // 비밀번호 삭제.
            userMapper.userSignup(uid, email, token);
        } catch (MessagingException e) {
            JSON.put("statusCode", 500);
            JSON.put("statusMsg","Internal server Error");
            LOG.error("Internal server Error", e);
        }
        JSON.put("statusCode",200);
        JSON.put("statusMsg", "success");
        return JSON;
    }

    @RequestMapping(value = "/signin", method = RequestMethod.POST)
    public JSONObject signIn(@RequestBody Signin param) {
        JSONObject JSON = new JSONObject();
        String email = param.getEmail();
        System.out.println(email);
        UserModel userInfo = userMapper.getUser(email);
        if(userInfo == null){
            JSON.put("statusCode", 700);
            JSON.put("statusMsg", "Invalid Email");
            LOG.warn("Invalid Email");
            return JSON;
        }
        JSONObject Session = new JSONObject();
        System.out.println(userInfo.toString());
       // Session.put("uid", userInfo.getUid());
        Session.put("email",email);
        System.out.println(Session.toString());
        JSON.put("statusCode", 200);
        JSON.put("statusMsg", 200);
        JSON.put("accessToken", jwtService.accessToken(Session.toString()));
        JSON.put("refreshToken", jwtService.refereshToken(Session.toString()));
        return JSON;
    }
    /* 비밀번호 변경을 위한 이메일 전송
    사용자가에게 다시 이메일을 전송하고 임시로 디비에 저장해두었던 temp_password를 password로 update 해준다.
    사용자가 새로운 비밀번호를 입력하고 확인을 눌렀을 때, 이메일을 발송하고 사용자 상태를 다시'C'로 변경
    사용자가 이메일 인증에 성공하면 상태를 'T'로 변경하고 새로운 비밀번호로 update 해준다.
     */
    @RequestMapping(value="/changePassword", method=RequestMethod.POST)
    public JSONObject changePassword(@RequestBody ChangePassword param){
        JSONObject JSON = new JSONObject();
        String email = param.getEmail();
        String temp_password = Encoder.encrypt(param.getNewPassword());

        // 등록된 이메일이 아니면 BAD_REQUEST
        UserModel user = userMapper.getUser(email);
        if(user == null){
            JSON.put("statusCode", HttpStatus.BAD_REQUEST);
            JSON.put("statusMsg", "Email exist");
            LOG.warn("Email exist");
            return JSON;
        }
        // DB에 temp_password 저장, state = 'C'
        userMapper.stateUpdate(email);
        userMapper.temp_passwordUpdate(email, temp_password);

        // 비밀번호 변경 시, 이메일 인증을 위한 토큰 재발급
        char[] charaters = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','0','1','2','3','4','5','6','7','8','9'};
        StringBuffer sb = new StringBuffer();

        // 토큰 생성
        Random rn = new Random();
        for( int i = 0 ; i < 13 ; i++ ){
            sb.append( charaters[ rn.nextInt( charaters.length ) ] );
        }
        String token = sb.toString();
        // 이메일을 보냄
        MimeMessage msg = emailSender.createMimeMessage();
        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(msg, false, "UTF-8");
            // 링크 클릭 시, 새 창이 열리고 인증이 완료되고 확인을 클릭하면 자동으로 창이 없어지게.. (사용자가 사용하던 이메일 창을 그대로 남아있게)
            String text = "<html><body><p>인증을 위해 아래 링크를 클릭해 주세요</p>" +
                    "<a target='_blank' href='http://localhost:8080/api/cert2?&token=" + token + "'>여기를 눌러 인증해주세요</a></body></html>";
            helper.setTo(email);
            helper.setSubject("Team Groot - 이메일 인증");
            helper.setText(text,true);
            emailSender.send(msg);
            // token 업데이트
            userMapper.tokenUpdate(email, token);
        }catch (MessagingException e) {
            JSON.put("statusCode", HttpStatus.INTERNAL_SERVER_ERROR);
            JSON.put("statusMsg", "Internal server Error");
            LOG.error("Internal server Error", e);
        }
        JSON.put("statusCode", HttpStatus.NO_CONTENT);
        JSON.put("statusMsg", "success");
        return JSON;
    }
}