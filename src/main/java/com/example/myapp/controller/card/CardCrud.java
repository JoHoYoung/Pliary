package com.example.myapp.controller.card;

import java.util.List;
import java.util.UUID;

import com.example.myapp.ErrorCode;
import com.example.myapp.context.request.card.CreateCard;
import com.example.myapp.context.user.Session;
import com.example.myapp.exception.ExceedMaximumCardNumberException;
import com.example.myapp.mapper.CardMapper;
import com.example.myapp.mapper.UserMapper;
import com.example.myapp.model.CardModel;
import com.example.myapp.response.BaseResponse;
import com.example.myapp.response.DataListResponse;
import com.example.myapp.response.DataResponse;
import com.example.myapp.util.Util;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/card")
public class CardCrud {


  @Autowired
  CardMapper cardMapper;

  @Autowired
  UserMapper userMapper;

  @Autowired
  ObjectMapper objectMapper;

  // 유저 id, 식물 이름, 애칭, 시작 날짜, 주기
  @RequestMapping(value = "/create", method = RequestMethod.POST)
  public ResponseEntity<BaseResponse> createCard(@RequestAttribute("session") Session session, @RequestBody CreateCard param) {

    if (cardMapper.countCard(session.getId()) > 5) {
      throw new ExceedMaximumCardNumberException(ErrorCode.EXCEED_MAX_CARD_NUMBER);
    }
    // 카드 생성
    String id = UUID.randomUUID().toString();
    String name = param.getName();
    String nickName = param.getNickname();

    int initPeriod = param.getInitPeriod();
    cardMapper.createCard(id, session.getId(), name, nickName, initPeriod);

    JSONObject data = new JSONObject();
    data.put("id", id);
    final BaseResponse response = new DataResponse<>(200, "success", data);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @RequestMapping(value = "/update", method = RequestMethod.POST)
  public ResponseEntity<BaseResponse> updateCard(@RequestAttribute("session") Session session, @RequestBody CreateCard param) {

    String id = param.getId();
    String name = param.getName();
    String nickName = param.getNickname();
    int initPeriod = param.getInitPeriod();

    String userId = cardMapper.getUserId(id);

    Util.DataAthorization(userId,session.getId());

    // update
    cardMapper.updateCard(id, name, nickName, initPeriod, initPeriod);
    final BaseResponse response = new BaseResponse(200, "success");
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @RequestMapping(value = "/readAll", method = RequestMethod.GET)
  public ResponseEntity<BaseResponse> readAllCard(@RequestAttribute("session") Session session) {
    List<CardModel> Cards = cardMapper.readAllCard(session.getId());
    final BaseResponse response = new DataListResponse<>(200, "success", Cards);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @RequestMapping(value = "/read", method = RequestMethod.GET)
  public ResponseEntity<BaseResponse> readCard(@RequestAttribute("session") Session session, @RequestParam("id") String id) {
    String userId = cardMapper.getUserId(id);

    Util.DataAthorization(userId,session.getId());

    CardModel Card = cardMapper.readCard(id);
    final BaseResponse response = new DataResponse<>(200, "success", Card);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @RequestMapping(value = "/delete", method = RequestMethod.GET)
  public ResponseEntity<BaseResponse> deleteCard(@RequestAttribute("session") Session session, @RequestParam("id") String id) {
    String userId = cardMapper.getUserId(id);

    Util.DataAthorization(userId,session.getId());

    cardMapper.deleteCard(id);
    final BaseResponse response = new BaseResponse(200, "success");
    return new ResponseEntity<>(response, HttpStatus.OK);
  }
}
