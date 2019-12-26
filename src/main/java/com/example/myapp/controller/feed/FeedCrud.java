package com.example.myapp.controller.feed;

import com.example.myapp.context.request.feed.CreateFeed;
import com.example.myapp.mapper.CardMapper;
import com.example.myapp.mapper.FeedMapper;
import com.example.myapp.model.CardModel;
import com.example.myapp.model.FeedModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

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
  public JSONObject createFeed(HttpServletRequest req, @RequestBody CreateFeed param) {
    JSONObject JSON = new JSONObject();
    try {
      String id = UUID.randomUUID().toString();
      CardModel cardInfo = cardMapper.readCard(param.getCardId());
      feedMapper.createFeed(id, param.getCardId(), cardInfo.getNowPeriod());
      JSON.put("statusCode", 200);
      JSON.put("statusMsg", "success");
      return JSON;
    } catch (Exception e) {
      JSON.put("statusCode", 500);
      JSON.put("statusMsg", "Internal Server Error");
      return JSON;
    }
  }

  @RequestMapping(value = "/read", method = RequestMethod.POST)
  public JSONObject readFeed(HttpServletRequest req, @RequestBody CreateFeed param) {
    JSONObject JSON = new JSONObject();
    try {
      List<FeedModel> Feeds = feedMapper.readAllFeed(param.getCardId());
      List<String> data = new ArrayList<>();
      for (int i = 0; i < Feeds.size(); i++) {
        data.add(objectMapper.writeValueAsString(Feeds.get(i)));
      }
      JSON.put("statusCode", 200);
      JSON.put("statusMsg", "success");
      JSON.put("data", data);
      return JSON;
    } catch (Exception e) {
      JSON.put("stautsCode", 500);
      JSON.put("statusMsg", "Internal Server Error");
      return JSON;
    }
  }

  @RequestMapping(value = "/update", method = RequestMethod.POST)
  public JSONObject updateFeed(HttpServletRequest req, @RequestBody CreateFeed param) {
    JSONObject JSON = new JSONObject();
    try {
      feedMapper.updateFeed(param.getId());
      JSON.put("statusCode", 200);
      JSON.put("statusMsg", "success");
      return JSON;
    } catch (Exception e) {
      JSON.put("stautsCode", 500);
      JSON.put("statusMsg", "Internal Server Error");
      return JSON;
    }
  }

  @RequestMapping(value = "/delete", method = RequestMethod.POST)
  public JSONObject deleteFeed(HttpServletRequest req, @RequestBody CreateFeed param) {
    JSONObject JSON = new JSONObject();
    try {
      feedMapper.deleteFeed(param.getId());
      JSON.put("statusCode", 200);
      JSON.put("statusMsg", "success");
      return JSON;
    } catch (Exception e) {
      JSON.put("stautsCode", 500);
      JSON.put("statusMsg", "Internal Server Error");
      return JSON;
    }
  }
}
