package com.example.myapp;

import com.example.myapp.response.ErrorResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.text.ParseException;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(ParseException.class)
  protected ResponseEntity<ErrorResponse> HttpMessageNotReadableExceptionHandler(HttpMessageNotReadableException e) {
    return new ErrorResponse(700,"Invalid Request Format");
  }

  @ExceptionHandler(JsonProcessingException.class)
  protected ErrorResponse JsonProcessingExceptionHandler(JsonProcessingException e){
    return new ErrorResponse(701,"Invalid Data Format");
  }

  @ExceptionHandler(ParseException.class)
  protected ErrorResponse ParseExceptionHandler(ParseException e){
    return new ErrorResponse(700,"Invalid Request Format");
  }

  @ExceptionHandler(Exception.class)
  protected ErrorResponse ExceptionHandler(Exception e){
    return new ErrorResponse(700,"Invalid Request Format");
  }

}
