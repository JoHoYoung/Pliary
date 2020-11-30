package com.example.myapp.controller;

import com.example.myapp.context.request.feed.CreateFeed;
import com.example.myapp.context.request.feed.UpdateFeed;
import com.example.myapp.context.user.Session;
import com.example.myapp.model.Feed;
import com.example.myapp.response.BaseResponse;
import com.example.myapp.response.DataListResponse;
import com.example.myapp.response.DataResponse;
import com.example.myapp.util.Util;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/feed")
public class FeedController {


  @Autowired
  ObjectMapper objectMapper;


}
