package com.example.myapp.controller.auth;

import com.example.myapp.ErrorCode;
import com.example.myapp.context.request.user.Signin;
import com.example.myapp.context.request.user.Signup;
import com.example.myapp.exception.InvalidEmailException;
import com.example.myapp.service.JwtService;
import com.example.myapp.mapper.UserMapper;
import com.example.myapp.response.BaseResponse;
import com.example.myapp.response.JwtResponse;
import com.example.myapp.service.MailerService;
import com.example.myapp.util.RandomString;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/auth", consumes = MediaType.APPLICATION_JSON_VALUE)
public class User {

  private static final Log LOG = LogFactory.getLog("com.example.myapp");

  @Autowired
  private JwtService jwtService;
  @Autowired
  private UserMapper userMapper;
  @Autowired
  private MailerService mailerService;
  @Autowired
  Environment env;



}