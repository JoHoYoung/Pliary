package com.example.myapp.controller.card;

import java.util.List;

import com.example.myapp.ErrorCode;
import com.example.myapp.context.request.card.CreateCard;
import com.example.myapp.context.request.card.UpdateCard;
import com.example.myapp.context.user.Session;
import com.example.myapp.exception.ExceedMaximumCardNumberException;
import com.example.myapp.mapper.CardMapper;
import com.example.myapp.mapper.UserMapper;
import com.example.myapp.model.Card;
import com.example.myapp.response.BaseResponse;
import com.example.myapp.response.DataListResponse;
import com.example.myapp.response.DataResponse;
import com.example.myapp.util.Util;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    cardMapper.createCard(session.getId(), param.getTypeId(), param.getName(), param.getNickname(),
      param.getEngName(), param.getKrName(), param.getWaterPeriod(), param.getRemainPeriod());

    final BaseResponse response = new BaseResponse(200, "success");
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @RequestMapping(value = "/update", method = RequestMethod.POST)
  public ResponseEntity<BaseResponse> updateCard(@RequestAttribute("session") Session session, @RequestBody UpdateCard param) {

    int userId = cardMapper.getUserId(param.getId());

    Util.numberDataAthorization(userId,session.getId());

    // update
    cardMapper.updateCard(param.getId(), param.getName(), param.getNickname()
      , param.getWaterPeriod(), param.getRemainPeriod());

    final BaseResponse response = new BaseResponse(200, "success");
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @RequestMapping(value = "/readAll", method = RequestMethod.GET)
  public ResponseEntity<BaseResponse> readAllCard(@RequestAttribute("session") Session session) {
    List<Card> Cards = cardMapper.readAllCard(session.getId());
    final BaseResponse response = new DataListResponse<>(200, "success", Cards);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @RequestMapping(value = "/read", method = RequestMethod.GET)
  public ResponseEntity<BaseResponse> readCard(@RequestAttribute("session") Session session, @RequestParam("id")int id) {
    int userId = cardMapper.getUserId(id);

    Util.numberDataAthorization(userId,session.getId());

    Card Card = cardMapper.readCard(id);
    final BaseResponse response = new DataResponse<>(200, "success", Card);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @RequestMapping(value = "/delete", method = RequestMethod.GET)
  public ResponseEntity<BaseResponse> deleteCard(@RequestAttribute("session") Session session, @RequestParam("id")int id) {
    int userId = cardMapper.getUserId(id);

    Util.numberDataAthorization(userId,session.getId());

    cardMapper.deleteCard(id);
    final BaseResponse response = new BaseResponse(200, "success");
    return new ResponseEntity<>(response, HttpStatus.OK);
  }
}
