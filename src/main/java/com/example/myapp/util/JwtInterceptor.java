package com.example.myapp.util;

import com.example.myapp.ErrorCode;
import com.example.myapp.exception.InvalidTokenException;
import com.example.myapp.jwt.JwtServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtInterceptor extends HandlerInterceptorAdapter {

  private static final Logger LOG = LogManager.getLogger(JwtInterceptor.class);

  JwtServiceImpl JWT = new JwtServiceImpl();

  @Override
  public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws ParseException {
    if (req.getHeader("Authorization") == null) {
      throw new InvalidTokenException(ErrorCode.EMPTY_TOKEN);
    }
    String[] authHeader = req.getHeader("Authorization").split(" ");
    String token = authHeader[1];
    JWT.verifyToken(token);

    JSONParser parser = new JSONParser();
    Object obj = parser.parse(JWT.decode(token));
    JSONObject jsonObj = (JSONObject) obj;
    req.setAttribute("session", jsonObj);

    return true;
  }
}