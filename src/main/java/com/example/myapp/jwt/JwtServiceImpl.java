package com.example.myapp.jwt;

import com.example.myapp.util.JwtInterceptor;
import io.jsonwebtoken.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service("jwtService")
public class JwtServiceImpl {

    private static final Logger LOG = LogManager.getLogger(JwtInterceptor.class);
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
        }catch (ExpiredJwtException e) {
            LOG.error("유효기간이 지난 token을 사용했습니다.", e);
            return 700;
        } catch (JwtException e) {
            LOG.error("jwt error..", e);
            return 701;
        }
    }

    // Token 해독 및 객체 생성
    public String decode(String token) {
        Claims Claim = Jwts.parser().setSigningKey(SALT).parseClaimsJws(token).getBody();
        return Claim.getSubject();
    }
}