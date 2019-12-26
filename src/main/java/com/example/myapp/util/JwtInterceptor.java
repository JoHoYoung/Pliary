package com.example.myapp.util;

import com.example.myapp.ErrorCode;
import com.example.myapp.exception.DecodedTokenParseError;
import com.example.myapp.exception.InvalidTokenException;
import com.example.myapp.jwt.JwtServiceImpl;
import com.example.myapp.context.user.Session;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtInterceptor extends HandlerInterceptorAdapter {

  private static final Logger LOG = LogManager.getLogger(JwtInterceptor.class);

  @Autowired
  JwtServiceImpl JWT;

  @Autowired
  ObjectMapper objectMapper;

  @Override
  public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) {
    try {
      if (req.getHeader("Authorization") == null) {
        throw new InvalidTokenException(ErrorCode.EMPTY_TOKEN);
      }
      String[] authHeader = req.getHeader("Authorization").split(" ");
      String token = authHeader[1];
      JWT.verifyToken(token);

      Session session = objectMapper.readValue(JWT.decode(token), Session.class);
      req.setAttribute("session", session);

      return true;
    } catch (IOException e) {
      throw new DecodedTokenParseError(ErrorCode.DECODED_TOKEN_PARSE_ERROR);
    }
  }
}