package com.example.myapp.controller.auth;

import com.example.myapp.jwt.JwtServiceImpl;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/auth", consumes = MediaType.APPLICATION_JSON_VALUE)
public class Token {

    @Autowired
    private JwtServiceImpl jwtService;
    @Autowired
    public JavaMailSender emailSender;

    @RequestMapping(value = "/refresh", method = RequestMethod.GET)
    public JSONObject refreshToken(@RequestParam("token") String token) {
        String decodeSubject = jwtService.decode(token); // decodeSubject = userEmail (?)
        JSONObject JSON = new JSONObject();
        JSON.put("statusCode", HttpStatus.OK);
        JSON.put("statusMsg", "success");
        JSON.put("accessToken", jwtService.accessToken(decodeSubject));
        JSON.put("refreshToken", jwtService.refereshToken(decodeSubject));
        return JSON;
    }
}
