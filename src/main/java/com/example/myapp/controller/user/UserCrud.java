package com.example.myapp.controller.user;

import com.example.myapp.mapper.CardMapper;
import com.example.myapp.mapper.DiaryMapper;
import com.example.myapp.mapper.FeedMapper;
import com.example.myapp.mapper.UserMapper;
import com.example.myapp.mapper.attachment.CardAttachmentMapper;
import com.example.myapp.mapper.attachment.DiaryAttachmentMapper;
import com.example.myapp.model.CardModel;
import com.example.myapp.model.DiaryModel;
import com.example.myapp.model.UserModel;
import com.example.myapp.util.ObjectMapperSingleTon;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/user")
public class UserCrud {

    private static final Logger LOG = LogManager.getLogger(UserCrud.class);

    @Autowired
    UserMapper userMapper;
    @Autowired
    CardMapper cardMapper;
    @Autowired
    FeedMapper feedMapper;
    @Autowired
    DiaryMapper diaryMapper;
    @Autowired
    CardAttachmentMapper cardAttachmentMapper;
    @Autowired
    DiaryAttachmentMapper diaryAttachmentMapper;

    ObjectMapper objectMapper = ObjectMapperSingleTon.getInstance();

    @RequestMapping(value ="/info", method=RequestMethod.GET)
    public JSONObject userInfo(HttpServletRequest req){
        JSONObject JSON = new JSONObject();
        try{
            JSONObject sesssion = (JSONObject) req.getAttribute("session");
            UserModel user = userMapper.getUser(sesssion.get("email").toString());
            JSON.put("statusCode", 200);
            JSON.put("statusMsg", "success");
            JSON.put("data", objectMapper.writeValueAsString(user));
            return JSON;
        }catch(Exception e){
            JSON.put("statusCode", 500);
            JSON.put("statusMsg", "Internal server error");
            LOG.error("Internal server error", e);
            return JSON;
        }
    }

    @RequestMapping(value ="/withdraw", method=RequestMethod.GET)
    public JSONObject userWithdraw(HttpServletRequest req){
        JSONObject JSON = new JSONObject();
        try{
            JSONObject sesssion = (JSONObject) req.getAttribute("session");
            UserModel user = userMapper.getUser(sesssion.get("email").toString());
            List<CardModel> cards = cardMapper.readAllCard(user.getUid());
            // 다이어리 Uid 저장 (diaryAttachment delete를 위함)
            List<DiaryModel> diaries = new ArrayList<>();

            for(int i = 0; i < cards.size(); ++i){
                feedMapper.deleteFeedFromCardId(cards.get(i).getUid());
                cardMapper.deleteCard(cards.get(i).getUid());
                diaryMapper.deleteAllDiary(cards.get(i).getUid());
                cardAttachmentMapper.deleteAttachment(cards.get(i).getUid());
                // 카드 마다 갖고 있는 다이어리 Uid를 List에 add
                diaries.add(diaryMapper.readAllDiary(cards.get(i).getUid()).get(i));
            }
            // diaryAttachment delete
            for(int i = 0; i < diaries.size(); ++i)
                diaryAttachmentMapper.deleteAttachment(diaries.get(i).getUid());

            userMapper.deleteUser(userMapper.getUser(sesssion.get("email").toString()).getUid());

            JSON.put("statusCode", 200);
            JSON.put("statusMsg", "success");
            return JSON;
        }catch(Exception e){
            JSON.put("statusCode", 500);
            JSON.put("statusMsg", "Internal server error");
            LOG.error("Internal server error", e);
            return JSON;
        }
    }
}
