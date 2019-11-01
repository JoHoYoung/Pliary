package com.example.myapp.controller.test;

import com.example.myapp.mapper.UserMapper;
import com.example.myapp.util.AwsS3Util;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;


@RestController
@RequestMapping(value = "/api2")
public class Test2Controller {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    AwsS3Util awsS3Util;

    // 이메일 인증
    @RequestMapping(value="/auth/cert", method = RequestMethod.GET)
    public String emailCertification(@RequestParam("token") String token){
        // 사용자가 이메일 인증을 했을 때, get으로 받은 token이 DB에 있는지 확인하고
        // 그 token을 갖고있는 사용자의 state를 인증이 완료된 상태로 변경해준다.
        int result = userMapper.emailCertification(token);
        String fail = "http://localhost:8080/static/certFail.html";
        String success = "http://localhost:8080/static/certCompletion.html";
        if(result == 1) return "redirect:" + success;
        else return "redirect:" + fail;
    }

    @RequestMapping(value="/test", method = RequestMethod.GET)
    public JSONObject myfunc(HttpServletRequest req){
        JSONObject JSON = new JSONObject();

        JSONObject data = new JSONObject();

        LocalDateTime currentDateTime = LocalDateTime.now();
        System.out.println("LLL");
        int a =0;
        for(int i=0;i<2000000000;i++){
            a+=i;
            a-=i;
            a+=i;
            a-=i;
            a+=i;

            a-=100000;
        }
        data.put("uid", ChronoUnit.MILLIS.between(currentDateTime,LocalDateTime.now()));
        JSON.put("statusCode", 200);
        JSON.put("statusMsg", "success");
        JSON.put("data",data);
        return JSON;
    }

//    @RequestMapping(value = "/test", method = RequestMethod.POST)
//    public JSONObject test(@RequestParam("userimage") MultipartFile files) throws java.io.IOException
//    {
//        JSONObject JSON = new JSONObject();
//        byte [] byteArr=files.getBytes();
//        a.fileUpload("groot.devdogs.kr","aaq2223",byteArr);
//        System.out.println("KK");
//        JSON.put("statusCode", 200);
//        JSON.put("statusMsg", "success");
//        return JSON;
//    }
}
