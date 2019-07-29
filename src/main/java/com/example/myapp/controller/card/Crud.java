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
@RequestMapping(value = "/auth") // (?)
public class Crud {

    @Autowired
    CardMapper cardMapper;

    @Autowired
    UserMapper userMapper;

    ObjectMapper objectMapper;
    // 유저 id, 식물 이름, 애칭, 시작 날짜, 주기
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public JSONObject createCard(HttpServletRequest req, @RequestBody CreateCard param){

        JSONObject session = (JSONObject)req.getAttribute("sesson");
        String user_id = userMapper.getUser((String)session.get("email")).getUid();

        int init_period = param.getInit_period();
        String name = param.getName();
        String nickName = param.getNickname();
        int count = cardMapper.cardCount(user_id);

        JSONObject JSON = new JSONObject();
        if(count > 5) {
            JSON.put("statusCode", "");
            JSON.put("statusMsg", "Over..");
            return JSON;
        }
        // 카드 생성
        String uid = UUID.randomUUID().toString();
        cardMapper.createCard(uid, user_id, name, nickName, init_period);
        JSON.put("statusCode", HttpStatus.OK);
        JSON.put("statusMsg", "success create");
        return JSON;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public JSONObject updateCard(HttpServletRequest req,@RequestBody CreateCard param){
        /* update 가능한 부분
            - 식물 명(종류)
            - 식물 애칭
            - 처음 만난 날
            - 물 주기
         */
        JSONObject session = (JSONObject)req.getAttribute("sesson");
        String user_id = userMapper.getUser((String)session.get("email")).getUid();

        String uid = param.getUid();
        String name = param.getName();
        String nickName = param.getNickname();
        int init_period = param.getInit_period();
        // update
        int result = cardMapper.updateCard(uid, name, nickName, init_period);

        JSONObject JSON = new JSONObject();
        if(result == 1){
            JSON.put("statusCode", HttpStatus.OK);
            JSON.put("statusMsg", "success update");
            return JSON;
        }

        JSON.put("statusCode", "");
        JSON.put("statusMsg", "");
        return JSON;
    }

    @RequestMapping(value = "/read", method = RequestMethod.POST)
    public JSONObject readCard(HttpServletRequest req, @RequestParam("id")String card_id){
        JSONObject JSON = new JSONObject();
        try{
            JSONObject session = (JSONObject)req.getAttribute("sesson");
            String user_id = userMapper.getUser((String)session.get("email")).getUid();
            // Get user's All card
            if(card_id == null){

                List<CardModel> Cards = cardMapper.readAllCard(user_id, card_id);
                if(Cards.size() == 0){
                    JSON.put("stautsCode",500);
                    JSON.put("statusMsg","No data");
                    return JSON;
                }
                List<String> data = new ArrayList<>();
                for(int i=0;i<Cards.size();i++){
                    data.add(objectMapper.writeValueAsString(Cards.get(0)));
                }
                JSON.put("stautsCode",200);
                JSON.put("statusMsg","sucsss");
                JSON.put("data",data.toString());
                return JSON;
                // Get user's one card(card_id)
            }else{
                CardModel Card = cardMapper.readCard(card_id);
                if(Card == null){
                    JSON.put("stautsCode",500);
                    JSON.put("statusMsg","No data");
                    return JSON;
                }
                JSON.put("stautsCode",200);
                JSON.put("statusMsg","sucsss");
                JSON.put("data",objectMapper.writeValueAsString(Card));
                return JSON;
            }
        }catch(Exception e){
            JSON.put("stautsCode",500);
            JSON.put("statusMsg","Internal Server Error");
        }
    }
}
