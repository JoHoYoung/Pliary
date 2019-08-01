package com.example.myapp.controller.card;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.example.myapp.context.CreateCard;
import com.example.myapp.mapper.CardMapper;
import com.example.myapp.mapper.UserMapper;
import com.example.myapp.model.CardModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping(value = "/card") // (?)
public class Crud {

    @Autowired
    CardMapper cardMapper;

    @Autowired
    UserMapper userMapper;

    ObjectMapper objectMapper = new ObjectMapper();

    // 유저 id, 식물 이름, 애칭, 시작 날짜, 주기
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public JSONObject createCard(HttpServletRequest req, @RequestBody CreateCard param) {

        JSONObject session = (JSONObject) req.getAttribute("session");
        System.out.println(session);
        String user_id = userMapper.getUser((String) session.get("email")).getUid();

        int init_period = param.getInit_period();
        String name = param.getName();
        String nickName = param.getNickname();
        int count = cardMapper.countCard(user_id);

        JSONObject JSON = new JSONObject();
        if (count > 5) {
            JSON.put("statusCode", "");
            JSON.put("statusMsg", "Over..");
            return JSON;
        }
        // 카드 생성
        String uid = UUID.randomUUID().toString();
        cardMapper.createCard(uid, user_id, name, nickName, init_period);
        JSON.put("statusCode", 200);
        JSON.put("statusMsg", "success");
        return JSON;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public JSONObject updateCard(HttpServletRequest req, @RequestBody CreateCard param) {
        JSONObject session = (JSONObject) req.getAttribute("sesson");
        String user_id = userMapper.getUser((String) session.get("email")).getUid();
        String uid = param.getUid();
        String name = param.getName();
        String nickName = param.getNickname();
        int init_period = param.getInit_period();
        // update
        int result = cardMapper.updateCard(uid, name, nickName, init_period);

        JSONObject JSON = new JSONObject();
        if (result == 1) {
            JSON.put("statusCode", 200);
            JSON.put("statusMsg", "success");
            return JSON;
        }

        JSON.put("statusCode", "");
        JSON.put("statusMsg", "");
        return JSON;
    }

    @RequestMapping(value = "/readAll", method = RequestMethod.GET)
    public JSONObject readAllCard(HttpServletRequest req) {
        JSONObject JSON = new JSONObject();
        try {
            JSONObject session = (JSONObject) req.getAttribute("session");
            String user_id = userMapper.getUser((String) session.get("email")).getUid();
            // Get user's All card
            List<CardModel> Cards = cardMapper.readAllCard(user_id);
            if (Cards.size() == 0) {
                JSON.put("stautsCode", 500);
                JSON.put("statusMsg", "No data");
                return JSON;
            }
            List<String> data = new ArrayList<>();
            for (int i = 0; i < Cards.size(); i++) {
                data.add(objectMapper.writeValueAsString(Cards.get(i)));
            }
            JSON.put("stautsCode", 200);
            JSON.put("statusMsg", "sucsss");
            JSON.put("data", data);
            return JSON;
            // Get user's one card(card_id)

        } catch (Exception e) {
            System.out.println(e);
            JSON.put("stautsCode", 500);
            JSON.put("statusMsg", "Internal Server Error");
            return JSON;
        }
    }

    @RequestMapping(value = "/read", method = RequestMethod.GET)
    public JSONObject readCard(HttpServletRequest req, @RequestParam("id") String card_id) {
        JSONObject JSON = new JSONObject();
        try {
            JSONObject session = (JSONObject) req.getAttribute("session");
            String user_id = userMapper.getUser((String) session.get("email")).getUid();
            // Get user's All card
            CardModel Card = cardMapper.readCard(user_id, card_id);
            if (Card == null) {
                JSON.put("stautsCode", 500);
                JSON.put("statusMsg", "No data");
                return JSON;
            }
            JSON.put("stautsCode", 200);
            JSON.put("statusMsg", "sucsss");
            JSON.put("data", objectMapper.writeValueAsString(Card));
            return JSON;

        } catch (Exception e) {
            JSON.put("stautsCode", 500);
            JSON.put("statusMsg", "Internal Server Error");
            return JSON;
        }
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public JSONObject deleteCard(HttpServletRequest req, @RequestParam("id") String card_id) {
        JSONObject JSON = new JSONObject();
        try {
            JSONObject session = (JSONObject) req.getAttribute("session");
            String user_id = userMapper.getUser((String) session.get("email")).getUid();
            // Get user's All card
            cardMapper.deleteCard(card_id);
            JSON.put("stautsCode", 200);
            JSON.put("statusMsg", "sucsss");
            return JSON;
        } catch (Exception e) {
            JSON.put("stautsCode", 500);
            JSON.put("statusMsg", "Internal Server Error");
            return JSON;
        }
    }
}
