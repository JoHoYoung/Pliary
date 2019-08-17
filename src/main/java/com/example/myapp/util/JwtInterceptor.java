package com.example.myapp.util;

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
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) {
        JSONObject JSON = new JSONObject();

        if(req.getHeader("Authorization") == null){
            res.setContentType("application/json");
            JSON.put("statusCode", 701);
            JSON.put("statusMsg", "Empty Token");
            return false;
        }

        String[] authHeader = req.getHeader("Authorization").split(" ");
        String token = authHeader[1];
        // 테스트용
        int code = JWT.verifyToken(token);
        System.out.println(code);
        if (code == 700) {
            // expired token
            res.setContentType("application/json");
            JSON.put("statusCode", 700);
            JSON.put("statusMsg", "Token expired");
            res.setCharacterEncoding("UTF-8");
            LOG.warn("만료된 토큰입니다.");
            try {
                res.getWriter().write(JSON.toJSONString());
            } catch (IOException e) {
                LOG.warn("Client 연결 끊김", e);
            }
            return false;
        }
        if (code == 701) {
            //invalid token
            res.setContentType("application/json");
            JSON.put("statusCode", 700);
            JSON.put("statusMsg", "Invalid Token");
            res.setCharacterEncoding("UTF-8");
            LOG.error("잘못된 토큰입니다.");
            try {
                res.getWriter().write(JSON.toJSONString());
            } catch (IOException e) {
                LOG.warn("Client 연결 끊김", e);
            }
            return false;
        }
        JSONParser parser = new JSONParser();
        Object obj = null;
        try {
            obj = parser.parse(JWT.decode(token));
        } catch (ParseException e) {
            LOG.warn("parse error", e);
        }
        JSONObject jsonObj = (JSONObject) obj;
        req.setAttribute("session", jsonObj);
        return true;
    }
}