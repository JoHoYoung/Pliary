package com.example.myapp.service;

import com.example.myapp.ErrorCode;
import com.example.myapp.context.user.Session;
import com.example.myapp.exception.PliaryException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtInterceptor extends HandlerInterceptorAdapter {

  @Autowired
  JwtService JWT;

  @Autowired
  ObjectMapper objectMapper;

  @Override
  public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) {
    try {
      if (req.getHeader("Authorization") == null) {
        throw new PliaryException(ErrorCode.EMPTY_TOKEN);
      }
      String[] authHeader = req.getHeader("Authorization").split(" ");
      String token = authHeader[1];
      JWT.verifyToken(token);

      Session session = objectMapper.readValue(JWT.decode(token), Session.class);
      req.setAttribute("session", session);
      return true;

    } catch (IOException e) {
      throw new PliaryException(ErrorCode.DECODED_TOKEN_PARSE_ERROR);
    }
  }
}