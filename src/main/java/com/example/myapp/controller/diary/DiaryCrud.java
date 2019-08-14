package com.example.myapp.controller.diary;


import com.example.myapp.context.diary.CreateDiary;
import com.example.myapp.mapper.DiaryMapper;
import com.example.myapp.model.DiaryModel;
import com.example.myapp.util.ObjectMapperSingleTon;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.cj.x.protobuf.MysqlxDatatypes;
import org.apache.http.HttpRequest;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/diary") // (?)
public class DiaryCrud {

    @Autowired
    DiaryMapper diaryMapper;

    ObjectMapper objectMapper = ObjectMapperSingleTon.getInstance();

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public JSONObject createDiary(HttpServletRequest req, @RequestBody CreateDiary param){
        JSONObject JSON = new JSONObject();
        try{
            String uid = UUID.randomUUID().toString() ;
            String title = param.getTitle();
            String card_id = param.getCard_id();
            String body = param.getBody();
            diaryMapper.createDiary(uid, title, card_id, body);
            JSON.put("statusCode",200);
            JSON.put("statusMsg", "success");
            return JSON;
        }catch(Exception e){
            JSON.put("statusCode",500);
            JSON.put("statusMsg", "Internal server error");
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
            diaryMapper.updateDiary(title, body, uid);
            JSON.put("statusCode",200);
            JSON.put("statusMsg", "success");
            return JSON;
        }catch(Exception e){
            JSON.put("statusCode",500);
            JSON.put("statusMsg", "Internal server error");
            return JSON;
        }
    }

    @RequestMapping(value ="/read", method = RequestMethod.GET)
    public JSONObject readDiary(HttpServletRequest req, @RequestParam("id")String id){
        JSONObject JSON = new JSONObject();
        try{
            DiaryModel diary = diaryMapper.readDiary(id);
            JSON.put("statusCode",200);
            JSON.put("statusMsg", "success");
            JSON.put("data",objectMapper.writeValueAsString(diary));
            return JSON;
        }catch(Exception e){
            JSON.put("statusCode",500);
            JSON.put("statusMsg", "Internal server error");
            return JSON;
        }
    }

    @RequestMapping(value ="/readAll", method = RequestMethod.GET)
    public JSONObject readAllDiary(HttpServletRequest req, @RequestParam("id")String id){
        JSONObject JSON = new JSONObject();
        try{
            ArrayList<String> data = new ArrayList<>();
            List<DiaryModel> diaries = diaryMapper.readAllDiary(id);

            for(int i =0;i<diaries.size();i++){
                data.add(objectMapper.writeValueAsString(diaries.get(i)));
            }
            JSON.put("statusCode",200);
            JSON.put("statusMsg", "success");
            JSON.put("data",data);
            return JSON;
        }catch(Exception e){
            JSON.put("statusCode",500);
            JSON.put("statusMsg", "Internal server error");
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
            return JSON;
        }
    }

}
