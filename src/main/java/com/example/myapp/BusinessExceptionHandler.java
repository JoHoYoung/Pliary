package com.example.myapp;

import com.example.myapp.exception.*;
import com.example.myapp.response.ErrorResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.api.Http;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.text.ParseException;

@RestControllerAdvice
public class BusinessExceptionHandler {

  @ExceptionHandler(EmailSendException.class)
  protected  ResponseEntity<ErrorResponse> EmailSendExceptionHandler(EmailSendException e){
    final ErrorResponse response =  new ErrorResponse(e.getErrorCode());
    return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
  }

  @ExceptionHandler(InvalidEmailException.class)
  protected ResponseEntity<ErrorResponse> InvalidEmailExeptionHanlder(InvalidEmailException e){
    final ErrorResponse response = new ErrorResponse(e.getErrorCode());
    return new ResponseEntity(response, HttpStatus.FORBIDDEN);
  }

  @ExceptionHandler(InvalidTokenException.class)
  protected ResponseEntity<ErrorResponse> InvalidTokenExceptionHandler(InvalidTokenException e){
    final ErrorResponse response = new ErrorResponse(e.getErrorCode());
    return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
  }

  @ExceptionHandler(TokenExpiredException.class)
  protected ResponseEntity<ErrorResponse> TokenExpiredExceptionHandler(TokenExpiredException e){
    final ErrorResponse response = new ErrorResponse(e.getErrorCode());
    return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
  }


//
//  @ExceptionHandler(Exception.class)
//  protected ErrorResponse ExceptionHandler(Exception e){
//    return new ErrorResponse(700,"Invalid Request Format");
//  }

}
