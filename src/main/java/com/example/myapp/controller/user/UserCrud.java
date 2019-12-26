package com.example.myapp.controller.user;

import com.example.myapp.mapper.CardMapper;
import com.example.myapp.mapper.FeedMapper;
import com.example.myapp.mapper.UserMapper;
import com.example.myapp.model.CardModel;
import com.example.myapp.model.UserModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(value = "/user")
public class UserCrud {

    @Autowired
    UserMapper userMapper;

    @Autowired
    CardMapper cardMapper;

    @Autowired
    FeedMapper feedMapper;

    @Autowired
    ObjectMapper objectMapper;

    @RequestMapping(value ="/info", method=RequestMethod.GET)
    public JSONObject userInfo(HttpServletRequest req){
        JSONObject JSON = new JSONObject();
        try{
            JSONObject sesssion = (JSONObject) req.getAttribute("session");
            UserModel user = userMapper.getUser(sesssion.get("email").toString());
            JSON.put("statusCode",200);
            JSON.put("statusMsg","success");
            JSON.put("data", objectMapper.writeValueAsString(user));
            return JSON;
        }catch(Exception e){
            JSON.put("statusCode", 500);
            JSON.put("statusMsg", "Internal server error");
            return JSON;
        }
    }

    @RequestMapping(value ="/withdraw", method=RequestMethod.GET)
    public JSONObject userWithdraw(HttpServletRequest req){
        JSONObject JSON = new JSONObject();
        try{
            JSONObject sesssion = (JSONObject) req.getAttribute("session");
            UserModel user = userMapper.getUser(sesssion.get("email").toString());
            List<CardModel> cards = cardMapper.readAllCard(user.getId());
            for(int i=0;i<cards.size();i++){
                feedMapper.deleteFeedFromCardId(cards.get(i).getId());
                cardMapper.deleteCard(cards.get(i).getId());
            }
            JSON.put("statusCode",200);
            JSON.put("statusMsg","success");
            return JSON;
        }catch(Exception e){
            JSON.put("statusCode", 500);
            JSON.put("statusMsg", "Internal server error");
            return JSON;
        }
    }
}
