package com.example.myapp;

import com.example.myapp.exception.*;
import com.example.myapp.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BusinessExceptionHandler {

  @ExceptionHandler(PliaryException.class)
  protected ResponseEntity<ErrorResponse> DateParseExceptionHandler(PliaryException e) {
    final ErrorResponse response = new ErrorResponse(e.getErrorCode());
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }


}
