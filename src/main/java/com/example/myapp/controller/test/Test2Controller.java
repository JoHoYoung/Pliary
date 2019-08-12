package com.example.myapp.controller.test;

import com.example.myapp.mapper.UserMapper;
import com.example.myapp.util.AwsS3Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.IIOException;
import java.util.List;

@Controller
@RequestMapping("/api2")
public class Test2Controller {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    AwsS3Util a;

    // 이메일 인증
    @RequestMapping(value="/auth/cert", method = RequestMethod.GET)
    public String emailCertification(@RequestParam("token") String token){
        // 사용자가 이메일 인증을 했을 때, get으로 받은 token이 DB에 있는지 확인하고
        // 그 token을 갖고있는 사용자의 state를 인증이 완료된 상태로 변경해준다.
        int result = userMapper.emailCertification(token);
        System.out.println(result);
        String fail = "http://localhost:8080/static/certFail.html";
        String success = "http://localhost:8080/static/certCompletion.html";
        if(result == 1) return "redirect:" + success;
        else return "redirect:" + fail;
    }

    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public String test(@RequestParam("userimage") MultipartFile files) throws java.io.IOException
    {
        byte [] byteArr=files.getBytes();
        a.fileUpload("groot.devdogs.kr","aaq123",byteArr);
        System.out.println("KK");
        return "GOOD";
    }
}
