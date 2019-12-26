package com.example.myapp.controller.auth;

import com.example.myapp.ErrorCode;
import com.example.myapp.context.request.user.Signin;
import com.example.myapp.context.request.user.Signup;
import com.example.myapp.exception.InvalidEmailException;
import com.example.myapp.jwt.JwtServiceImpl;
import com.example.myapp.mapper.UserMapper;
import com.example.myapp.response.BaseResponse;
import com.example.myapp.response.JwtResponse;
import com.example.myapp.util.Mailer;
import com.example.myapp.util.RandomString;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/auth", consumes = MediaType.APPLICATION_JSON_VALUE)
public class User {
  private static final Log LOG = LogFactory.getLog( "com.example.myapp");
  @Autowired
  private JwtServiceImpl jwtService;
  @Autowired
  private UserMapper userMapper;
  @Autowired
  private RandomString randomString;
  @Autowired
  private Mailer mailer;

  @RequestMapping(value = "/signup", method = RequestMethod.POST)
  public ResponseEntity<BaseResponse> Signup(@RequestBody Signup param) {
    String id = param.getId();
    String email = param.getEmail();

    // Duplicate Check
    if (userMapper.existUserEmail(email) != 0) {
      throw new InvalidEmailException(ErrorCode.DUPLICATED_EMAIL);
    }

    String token = randomString.generate();

    // Send verification mail, and insert to DB
    mailer.sendVerfyMail(email, token);
    userMapper.userSignup(id, email, token);

    final BaseResponse response = new BaseResponse(200,"success");
    return new ResponseEntity<>(response,HttpStatus.OK);
  }

  @RequestMapping(value = "/signin", method = RequestMethod.POST)
  public ResponseEntity<BaseResponse> signIn(@RequestBody Signin param) {
    String id = param.getId();

    LOG.error("TEST AT CONTROLLER");
    // Uid Valid Check
    if (userMapper.existUserId(id) == 0) {
      throw new InvalidEmailException(ErrorCode.INVALID_EMAIL);
    }

    // Gen Token
    JSONObject Session = new JSONObject();
    Session.put("id", id);

    final BaseResponse response = new JwtResponse(HttpStatus.OK.value(), "success",
      jwtService.accessToken(Session.toString()),
      jwtService.refereshToken(Session.toString()));

    return new ResponseEntity<>(response, HttpStatus.OK);
  }

}