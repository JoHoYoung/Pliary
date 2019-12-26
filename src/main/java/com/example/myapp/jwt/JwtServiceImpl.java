package com.example.myapp.jwt;

import com.example.myapp.exception.InvalidTokenException;
import com.example.myapp.exception.TokenExpiredException;
import com.example.myapp.ErrorCode;
import com.example.myapp.util.JwtInterceptor;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;

@Component
public class JwtServiceImpl {

  private static final String SALT = "grootsecret";

  public String accessToken(String subject) {
    Date Now = new Date();
    Date expireTime = new Date(Now.getTime() + 1000 * 60 * 60 * 24 * 14);
    String jwt = Jwts.builder()
      .setExpiration(expireTime)
      .setSubject(subject)
      .signWith(SignatureAlgorithm.HS256, SALT)
      .compact();
    return jwt;
  }

  public String refereshToken(String subject) {
    Date Now = new Date();
    Date expireTime = new Date(Now.getTime() + 1000 * 60 * 60 * 24 * 365 * 5);
    String jwt = Jwts.builder()
      .setExpiration(expireTime)
      .setSubject(subject)
      .signWith(SignatureAlgorithm.HS256, SALT)
      .compact();
    return jwt;
  }

  public void verifyToken(String token) {
    try {
      Jwts.parser().setSigningKey(SALT).parseClaimsJws(token).getBody();
    } catch (ExpiredJwtException e) {
      throw new TokenExpiredException(ErrorCode.JWT_TOKEN_EXPIRED);
    } catch (JwtException e) {
      throw new InvalidTokenException(ErrorCode.INVALID_TOKEN);
    }
  }

  // Token 해독 및 객체 생성
  public String decode(String token) {
    Claims Claim = Jwts.parser().setSigningKey(SALT).parseClaimsJws(token).getBody();
    return Claim.getSubject();
  }
}