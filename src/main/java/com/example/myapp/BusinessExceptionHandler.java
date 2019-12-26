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

  @ExceptionHandler(DateParseException.class)
  protected ResponseEntity<ErrorResponse> DateParseExceptionHandler(DateParseException e) {
    final ErrorResponse response = new ErrorResponse(e.getErrorCode());
    return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
  }

  @ExceptionHandler(DecodedTokenParseException.class)
  protected ResponseEntity<ErrorResponse> DecodedTokenParseExceptionHandler(DecodedTokenParseException e) {
    final ErrorResponse response = new ErrorResponse(e.getErrorCode());
    return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
  }

  @ExceptionHandler(EmailSendException.class)
  protected ResponseEntity<ErrorResponse> EmailSendExceptionHandler(EmailSendException e) {
    final ErrorResponse response = new ErrorResponse(e.getErrorCode());
    return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
  }

  @ExceptionHandler(ExceedMaximumCardNumberException.class)
  protected ResponseEntity<ErrorResponse> ExceedMaximumCardNumberExceptionHandler(ExceedMaximumCardNumberException e) {
    final ErrorResponse response = new ErrorResponse(e.getErrorCode());
    return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
  }

  @ExceptionHandler(InvalidEmailException.class)
  protected ResponseEntity<ErrorResponse> InvalidEmailExeptionHanlder(InvalidEmailException e) {
    final ErrorResponse response = new ErrorResponse(e.getErrorCode());
    return new ResponseEntity(response, HttpStatus.FORBIDDEN);
  }

  @ExceptionHandler(InvalidTokenException.class)
  protected ResponseEntity<ErrorResponse> InvalidTokenExceptionHandler(InvalidTokenException e) {
    final ErrorResponse response = new ErrorResponse(e.getErrorCode());
    return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
  }

  @ExceptionHandler(NotFoundException.class)
  protected ResponseEntity<ErrorResponse> NotFoundExceptionHandler(NotFoundException e) {
    final ErrorResponse response = new ErrorResponse(e.getErrorCode());
    return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
  }

  @ExceptionHandler(TokenExpiredException.class)
  protected ResponseEntity<ErrorResponse> TokenExpiredExceptionHandler(TokenExpiredException e) {
    final ErrorResponse response = new ErrorResponse(e.getErrorCode());
    return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
  }

  @ExceptionHandler(UnauthorizedAccessException.class)
  protected ResponseEntity<ErrorResponse> UnauthorizedAccessExceptionHandler(UnauthorizedAccessException e) {
    final ErrorResponse response = new ErrorResponse(e.getErrorCode());
    return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
  }

}
