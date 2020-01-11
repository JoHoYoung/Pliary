package com.example.myapp.controller.feed;

import com.example.myapp.context.request.feed.CreateFeed;
import com.example.myapp.context.request.feed.UpdateFeed;
import com.example.myapp.context.user.Session;
import com.example.myapp.mapper.CardMapper;
import com.example.myapp.mapper.FeedMapper;
import com.example.myapp.model.CardModel;
import com.example.myapp.model.FeedModel;
import com.example.myapp.response.BaseResponse;
import com.example.myapp.response.DataListResponse;
import com.example.myapp.response.DataResponse;
import com.example.myapp.util.Util;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
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

    int userId = cardMapper.getUserId(param.getCardId());
    Util.numberDataAthorization(userId, session.getId());

    feedMapper.createFeed(param.getCardId(), param.getOverDegree(), param.getFeedAt());

    final BaseResponse response = new BaseResponse(200, "success");
    return new ResponseEntity(response, HttpStatus.OK);

  }

  @RequestMapping(value = "/readAll", method = RequestMethod.GET)
  public ResponseEntity readAllFeed(@RequestAttribute("session") Session session, @RequestParam("id") int id){
    int userId = cardMapper.getUserId(id);
    Util.numberDataAthorization(userId, session.getId());
    List<FeedModel> Feeds = feedMapper.readAllFeed(id);
    final BaseResponse response = new DataListResponse<>(200, "success", Feeds);
    return new ResponseEntity(response, HttpStatus.OK);

  }

  @RequestMapping(value = "/read", method = RequestMethod.GET)
  public ResponseEntity readFeed(@RequestAttribute("session") Session session, @RequestParam("id") int id){

    int userId = feedMapper.getUserId(id);
    Util.numberDataAthorization(userId, session.getId());

    FeedModel Feed = feedMapper.readFeed(id);
    final BaseResponse response = new DataResponse<>(200, "success", Feed);
    return new ResponseEntity(response, HttpStatus.OK);

  }

  @RequestMapping(value = "/update", method = RequestMethod.POST)
  public ResponseEntity updateFeed(@RequestAttribute("session") Session session, @RequestBody UpdateFeed param) {

    int userId = feedMapper.getUserId(param.getId());

    Util.numberDataAthorization(userId, session.getId());
    feedMapper.updateFeed(param.getId(), param.getOverDegree(), param.getFeedAt());

    final BaseResponse response = new BaseResponse(200, "success");
    return new ResponseEntity(response, HttpStatus.OK);

  }

  @RequestMapping(value = "/delete", method = RequestMethod.GET)
  public ResponseEntity deleteFeed(@RequestAttribute("session") Session session, @RequestParam("id") int id) {

    int userId = feedMapper.getUserId(id);
    Util.numberDataAthorization(userId, session.getId());

    feedMapper.deleteFeed(id);

    final BaseResponse response = new BaseResponse(200, "success");
    return new ResponseEntity(response, HttpStatus.OK);

  }

}
