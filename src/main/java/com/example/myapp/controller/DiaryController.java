package com.example.myapp.controller;

import com.example.myapp.context.request.diary.CreateDiary;
import com.example.myapp.context.request.diary.UpdateDiary;
import com.example.myapp.context.user.Session;
import com.example.myapp.model.Diary;
import com.example.myapp.response.BaseResponse;
import com.example.myapp.response.DataListResponse;
import com.example.myapp.response.DataResponse;
import com.example.myapp.util.Util;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping(value = "/diary")
public class DiaryController {


}
