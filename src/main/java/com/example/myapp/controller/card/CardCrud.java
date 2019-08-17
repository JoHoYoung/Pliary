package com.example.myapp.controller.card;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.example.myapp.context.card.CreateCard;
import com.example.myapp.mapper.CardMapper;
import com.example.myapp.mapper.UserMapper;
import com.example.myapp.model.CardModel;
import com.example.myapp.util.ObjectMapperSingleTon;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping(value = "/card")
public class CardCrud {

    private static final Logger LOG = LogManager.getLogger(CardCrud.class);

    @Autowired
    CardMapper cardMapper;

    @Autowired
    UserMapper userMapper;

    ObjectMapper objectMapper = ObjectMapperSingleTon.getInstance();

    // 유저 id, 식물 이름, 애칭, 시작 날짜, 주기
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public JSONObject createCard(HttpServletRequest req, @RequestBody CreateCard param) {

        JSONObject session = (JSONObject) req.getAttribute("session");
        JSONObject JSON = new JSONObject();
        System.out.println(session);
        String user_id = userMapper.getUser((String) session.get("email")).getUid();
        int count = cardMapper.countCard(user_id);

        if (count > 5) {
            JSON.put("statusCode", 400);
            JSON.put("statusMsg", "Cannot make card");
            return JSON;
        }
        // 카드 생성
        String uid = UUID.randomUUID().toString();
        cardMapper.createCard(uid, user_id, null, null, 0);
        JSONObject data = new JSONObject();
        data.put("uid", uid);

        JSON.put("statusCode", 200);
        JSON.put("statusMsg", "success");
        JSON.put("data",data);
        return JSON;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public JSONObject updateCard(HttpServletRequest req, @RequestBody CreateCard param) {
        JSONObject JSON = new JSONObject();
        String uid = param.getUid();
        String name = param.getName();
        String nickName = param.getNickname();
        int init_period = param.getInit_period();
        // update
        cardMapper.updateCard(uid, name, nickName, init_period, init_period);
        JSON.put("statusCode", 200);
        JSON.put("statusMsg", "success");
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
                LOG.info("/readAll : No data");
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
            JSON.put("stautsCode", 500);
            JSON.put("statusMsg", "Internal Server Error");
            LOG.warn("Internal Server Error", e);
            return JSON;
        }
    }

    @RequestMapping(value = "/read", method = RequestMethod.GET)
    public JSONObject readCard(HttpServletRequest req, @RequestParam("id") String card_id) {
        JSONObject JSON = new JSONObject();
        try {
            CardModel Card = cardMapper.readCard(card_id);
            if (Card == null) {
                JSON.put("stautsCode", 500);
                JSON.put("statusMsg", "No data");
                LOG.info("/read : No data");
                return JSON;
            }
            JSON.put("stautsCode", 200);
            JSON.put("statusMsg", "sucsss");
            JSON.put("data", objectMapper.writeValueAsString(Card));
            return JSON;

        } catch (Exception e) {
            JSON.put("stautsCode", 500);
            JSON.put("statusMsg", "Internal Server Error");
            LOG.error("Internal Server Error", e);
            return JSON;
        }
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public JSONObject deleteCard(HttpServletRequest req, @RequestParam("id") String card_id) {
        JSONObject JSON = new JSONObject();
        try {
            cardMapper.deleteCard(card_id);
            JSON.put("stautsCode", 200);
            JSON.put("statusMsg", "sucsss");
            return JSON;
        } catch (Exception e) {
            JSON.put("stautsCode", 500);
            JSON.put("statusMsg", "Internal Server Error");
            LOG.error("Internal Server Error", e);
            return JSON;
        }
    }
}
