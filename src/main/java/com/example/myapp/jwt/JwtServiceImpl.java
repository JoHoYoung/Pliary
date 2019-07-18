package com.example.myapp.jwt;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service("jwtService")
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

    public int verifyToken(String token) {
        try{
            Jwts.parser().setSigningKey(SALT).parseClaimsJws(token).getBody();
            return 200;
        }catch (ExpiredJwtException exception) {
            return 700;
        } catch (JwtException exception) {
            return 701;
        }
    }

    // Token 해독 및 객체 생성
    public String decode(String token) {
        Claims Claim = Jwts.parser().setSigningKey(SALT).parseClaimsJws(token).getBody();
        return Claim.getSubject();
    }
}