package com.example.myapp.controller.auth;

import com.example.myapp.context.CreateCard;
import com.example.myapp.mapper.PlantCardsMapper;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/auth") // (?)
public class PlantCards {

    @Autowired
    PlantCardsMapper plantCardsMapper;

    // 유저 id, 식물 이름, 애칭, 시작 날짜, 주기
    @RequestMapping(value = "/createCard", method = RequestMethod.POST)
    public JSONObject createCard(@RequestBody CreateCard param){
        int user_id = param.getUser_id();
        String name = param.getName();
        String nickName = param.getNickname();
        String birth = param.getStart_date();
        int cycle = param.getCycle();

        int count = plantCardsMapper.cardCount(user_id);

        JSONObject JSON = new JSONObject();
        if(count > 5) {
            JSON.put("statusCode", "");
            JSON.put("statusMsg", "Over..");
            return JSON;
        }

        // 카드 생성
        plantCardsMapper.createCard(user_id, name, nickName, birth, cycle);
        JSON.put("statusCode", HttpStatus.OK);
        JSON.put("statusMsg", "success create");
        return JSON;
    }

    @RequestMapping(value = "/updateForm", method = RequestMethod.POST)
    public JSONObject updateForm(@RequestBody CreateCard param){
        /* update 가능한 부분
            - 식물 명(종류)
            - 식물 애칭
            - 처음 만난 날
            - 물 주기
         */
        int uid = param.getUid();
        String name = param.getName();
        String nickName = param.getNickname();
        String birth = param.getStart_date();
        int cycle = param.getCycle();

        // update
        int result = plantCardsMapper.updateForm(uid, name, nickName, birth, cycle);

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
}
