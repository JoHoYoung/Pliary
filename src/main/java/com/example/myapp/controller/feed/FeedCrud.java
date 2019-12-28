package com.example.myapp.controller.feed;

import com.example.myapp.context.request.feed.CreateFeed;
import com.example.myapp.context.user.Session;
import com.example.myapp.mapper.CardMapper;
import com.example.myapp.mapper.FeedMapper;
import com.example.myapp.model.CardModel;
import com.example.myapp.model.FeedModel;
import com.example.myapp.response.BaseResponse;
import com.example.myapp.response.DataListResponse;
import com.example.myapp.util.Util;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/feed")
public class FeedCrud {

  @Autowired
  FeedMapper feedMapper;
  @Autowired
  CardMapper cardMapper;
  @Autowired
  ObjectMapper objectMapper;

  @RequestMapping(value = "/create", method = RequestMethod.POST)
  public ResponseEntity createFeed(@RequestAttribute("session") Session session, @RequestBody CreateFeed param) {
    String id = UUID.randomUUID().toString();

    String userId = cardMapper.getUserId(param.getCardId());
    Util.DataAthorization(userId, session.getId());

    CardModel cardInfo = cardMapper.readCard(param.getCardId());
    feedMapper.createFeed(id, param.getCardId(), cardInfo.getNowPeriod());

    final BaseResponse response = new BaseResponse(200, "success");
    return new ResponseEntity(response, HttpStatus.OK);

  }

  @RequestMapping(value = "/read", method = RequestMethod.POST)
  public ResponseEntity readFeed(@RequestAttribute("session") Session session, @RequestBody CreateFeed param) {

    String userId = cardMapper.getUserId(param.getCardId());
    Util.DataAthorization(userId, session.getId());

    List<FeedModel> Feeds = feedMapper.readAllFeed(param.getCardId());
    final BaseResponse response = new DataListResponse<>(200, "success", Feeds);
    return new ResponseEntity(response, HttpStatus.OK);

  }

  @RequestMapping(value = "/update", method = RequestMethod.POST)
  public ResponseEntity updateFeed(@RequestAttribute("session") Session session, @RequestBody CreateFeed param) {

    String userId = cardMapper.getUserId(param.getCardId());

    Util.DataAthorization(userId, session.getId());
    feedMapper.updateFeed(param.getId());

    final BaseResponse response = new BaseResponse(200, "success");
    return new ResponseEntity(response, HttpStatus.OK);

  }

  @RequestMapping(value = "/delete", method = RequestMethod.POST)
  public ResponseEntity deleteFeed(@RequestAttribute("session") Session session, @RequestBody CreateFeed param) {

    String userId = cardMapper.getUserId(param.getCardId());
    Util.DataAthorization(userId, session.getId());

    feedMapper.deleteFeed(param.getId());

    final BaseResponse response = new BaseResponse(200, "success");
    return new ResponseEntity(response, HttpStatus.OK);

  }

}
