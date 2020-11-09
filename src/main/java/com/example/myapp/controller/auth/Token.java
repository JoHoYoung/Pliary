package com.example.myapp.controller.auth;

import com.example.myapp.context.request.token.Refresh;
import com.example.myapp.service.JwtService;
import com.example.myapp.response.BaseResponse;
import com.example.myapp.response.JwtResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/token", consumes = MediaType.APPLICATION_JSON_VALUE)
public class Token {

    @Autowired
    private JwtService jwtService;

    @RequestMapping(value = "/refresh", method = RequestMethod.POST)
    public ResponseEntity<BaseResponse> refreshToken(@RequestBody Refresh token) {

        jwtService.verifyToken(token.getAccessToken());
        jwtService.verifyToken(token.getRefreshToken());


        String decodeSubject = jwtService.decode(token.getAccessToken()); // decodeSubject = userEmail (?)

        final BaseResponse response = new JwtResponse(HttpStatus.OK.value(), "success",
                jwtService.accessToken(decodeSubject), token.getRefreshToken());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
