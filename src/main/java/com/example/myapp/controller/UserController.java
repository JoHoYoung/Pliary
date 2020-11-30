package com.example.myapp.controller;

import com.example.myapp.bo.user.UserBO;
import com.example.myapp.context.request.user.Signin;
import com.example.myapp.context.response.DataResponse;
import com.example.myapp.context.user.SigninResult;
import com.example.myapp.model.User;
import com.example.myapp.repository.UserRepository;
import com.example.myapp.service.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    UserBO userBO;

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtService jwtService;

    @PostMapping("/signin")
    public ResponseEntity signin(@RequestBody Signin signin) {
        String oauthToken = signin.getOauthToken();
        SigninResult signinResult = userBO.signin(oauthToken);
        return new ResponseEntity(new DataResponse<>(signinResult), HttpStatus.OK);
    }

    @RequestMapping("/signup")
    public ResponseEntity signup(@RequestBody User user){
        SigninResult signinResult = userBO.signup(user);
        return new ResponseEntity(new DataResponse<>(signinResult), HttpStatus.OK);
    }

}
