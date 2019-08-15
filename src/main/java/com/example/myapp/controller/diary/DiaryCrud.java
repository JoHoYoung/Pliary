package com.example.myapp.controller.diary;

import com.example.myapp.context.diary.CreateDiary;
import com.example.myapp.mapper.DiaryMapper;
import com.example.myapp.model.DiaryModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.UUID;

@RestController
@RequestMapping(value = "/diary")
public class DiaryCrud {

    private static final Logger LOG = LogManager.getLogger(DiaryCrud.class);

    @Autowired
    DiaryMapper diaryMapper;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public JSONObject createDiary(HttpServletRequest req, @RequestBody CreateDiary param){
        JSONObject JSON = new JSONObject();
        try{
            String uid = UUID.randomUUID().toString();
            String card_id = param.getCard_id();
            String title = param.getTitle();
            String body = param.getBody();
            diaryMapper.createDiary(uid, card_id, title, body);
            JSON.put("statusCode",200);
            JSON.put("statusMsg", "success");
            return JSON;
        }catch(Exception e){
            JSON.put("statusCode", 500);
            JSON.put("statusMsg", "Internal server error");
            LOG.error("Internal server error", e);
            return JSON;
        }
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public JSONObject updateDiary(HttpServletRequest req, @RequestBody CreateDiary param){
        JSONObject JSON = new JSONObject();
        try{
            String uid = param.getUid();
            if(uid == null){
                throw new Error("Empty uid");
            }
            String title = param.getTitle();
            String body = param.getBody();
            diaryMapper.updateDiary(uid, title, body);
            JSON.put("statusCode",200);
            JSON.put("statusMsg", "success");
            return JSON;
        }catch(Exception e){
            JSON.put("statusCode",500);
            JSON.put("statusMsg", "Internal server error");
            LOG.error("Internal server error", e);
            return JSON;
        }
    }

    @RequestMapping(value ="/read", method = RequestMethod.GET)
    // id = Diary.uid
    public JSONObject readDiary(HttpServletRequest req, @RequestParam("id") String id){
        JSONObject JSON = new JSONObject();
        try{
            DiaryModel diary = diaryMapper.readDiary(id);
            JSON.put("statusCode", 200);
            JSON.put("statusMsg", "success");
            JSON.put("data", diary);
            return JSON;
        }catch(Exception e){
            JSON.put("statusCode",500);
            JSON.put("statusMsg", "Internal server error");
            LOG.error("Internal server error", e);
            return JSON;
        }
    }

    @RequestMapping(value ="/readAll", method = RequestMethod.GET)
    // id = cardId
    public JSONObject readAllDiary(HttpServletRequest req, @RequestParam("id") String id){
        JSONObject JSON = new JSONObject();
        try{
            ArrayList<DiaryModel> diaries = diaryMapper.readAllDiary(id);
            JSON.put("statusCode",200);
            JSON.put("statusMsg", "success");
            JSON.put("data", diaries);
            return JSON;
        }catch(Exception e){
            JSON.put("statusCode",500);
            JSON.put("statusMsg", "Internal server error");
            LOG.error("Internal server error", e);
            return JSON;
        }
    }

    @RequestMapping(value ="/delete", method = RequestMethod.POST)
    public JSONObject deleteDiary(HttpServletRequest req, @RequestBody CreateDiary param){
        JSONObject JSON = new JSONObject();
        try{
            diaryMapper.deleteDiary(param.getUid());
            JSON.put("statusCode",200);
            JSON.put("statusMsg", "success");
            return JSON;
        }catch(Exception e){
            JSON.put("statusCode",500);
            JSON.put("statusMsg", "Internal server error");
            LOG.error("Internal server error", e);
            return JSON;
        }
    }

}
