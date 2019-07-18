package com.example.myapp.util;

import com.example.myapp.jwt.JwtServiceImpl;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Component
public class JwtInterceptor extends HandlerInterceptorAdapter {

    JwtServiceImpl JWT = new JwtServiceImpl();

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
        String[] authHeader = req.getHeader("Authorization").split(" ");
        String token = authHeader[1];
        JSONObject JSON = new JSONObject();
        // 테스트용
        int code = JWT.verifyToken(token);
        System.out.println(code);
        if (code == 700){
            // expired token
            res.setContentType("application/json");
            JSON.put("statusCode", 700);
            JSON.put("statusMsg","Token expired");
            res.setCharacterEncoding("UTF-8");
            res.getWriter().write(JSON.toJSONString());
            return false;
        }
        if (code == 701){
            //invalid token
            res.setContentType("application/json");
            JSON.put("statusCode", 700);
            JSON.put("statusMsg","Invalid Token");
            res.setCharacterEncoding("UTF-8");
            res.getWriter().write(JSON.toJSONString());
            return false;
        }
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(JWT.decode(token));
        JSONObject jsonObj = (JSONObject) obj;
        req.setAttribute("session",jsonObj);
        return true;
    }
}
