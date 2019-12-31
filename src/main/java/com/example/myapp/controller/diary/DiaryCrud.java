package com.example.myapp.controller.diary;

import com.example.myapp.context.request.diary.CreateDiary;
import com.example.myapp.context.user.Session;
import com.example.myapp.controller.card.CardCrud;
import com.example.myapp.mapper.CardMapper;
import com.example.myapp.mapper.DiaryMapper;
import com.example.myapp.model.DiaryModel;
import com.example.myapp.response.BaseResponse;
import com.example.myapp.response.DataListResponse;
import com.example.myapp.response.DataResponse;
import com.example.myapp.util.Util;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.Response;
import java.util.ArrayList;
import java.util.UUID;

@RestController
@RequestMapping(value = "/diary")
public class DiaryCrud {

  @Autowired
  DiaryMapper diaryMapper;

  @Autowired
  CardMapper cardMapper;

  @RequestMapping(value = "/create", method = RequestMethod.POST)
  public ResponseEntity<BaseResponse> createDiary(@RequestAttribute("session") Session session, @RequestBody CreateDiary param) {

    String id = UUID.randomUUID().toString();
    String cardId = param.getCardId();
    String title = param.getTitle();
    String body = param.getBody();

    diaryMapper.createDiary(id, cardId, title, body);

    JSONObject data = new JSONObject();
    data.put("id", id);
    final BaseResponse response = new DataResponse<>(200, "success", data);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @RequestMapping(value = "/update", method = RequestMethod.POST)
  public ResponseEntity updateDiary(@RequestAttribute("session") Session session, @RequestBody CreateDiary param) {
    String userId = cardMapper.getUserId(param.getCardId());

    Util.DataAthorization(userId, session.getId());

    diaryMapper.updateDiary(param.getId(), param.getTitle(), param.getTitle());
    final BaseResponse response = new BaseResponse(200, "success");
    return new ResponseEntity(response, HttpStatus.OK);
  }

  @RequestMapping(value = "/read", method = RequestMethod.GET)
  public ResponseEntity readDiary(@RequestAttribute("session") Session session, @RequestParam("id") String id) {

    String userId = diaryMapper.getUserId(id);
    Util.DataAthorization(userId, session.getId());

    DiaryModel diary = diaryMapper.readDiary(id);
    final BaseResponse response = new DataResponse<>(200, "success", diary);
    return new ResponseEntity(response, HttpStatus.OK);
  }

  @RequestMapping(value = "/readAll", method = RequestMethod.GET)
  // id = cardId
  public ResponseEntity readAllDiary(@RequestAttribute("session") Session session, @RequestParam("id") String id) {

    String userId = cardMapper.getUserId(id);
    Util.DataAthorization(userId, session.getId());

    ArrayList<DiaryModel> diaries = diaryMapper.readAllDiary(id);

    final BaseResponse response = new DataListResponse<>(200, "success", diaries);
    return new ResponseEntity<>(response, HttpStatus.OK);

  }
  @RequestMapping(value = "/delete", method = RequestMethod.GET)
  public ResponseEntity deleteDiary(@RequestAttribute("session") Session session, @RequestParam("id") String id) {

    String userId = diaryMapper.getUserId(id);
    Util.DataAthorization(userId, session.getId());
    diaryMapper.deleteDiary(id);
    final BaseResponse response = new BaseResponse(200, "success");
    return new ResponseEntity(response, HttpStatus.OK);

  }

}
