package com.example.myapp.controller;

import java.util.List;

import com.example.myapp.ErrorCode;
import com.example.myapp.context.request.card.CreateCard;
import com.example.myapp.context.request.card.UpdateCard;
import com.example.myapp.context.user.Session;
import com.example.myapp.exception.PliaryException;
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
public class CardController {

}
