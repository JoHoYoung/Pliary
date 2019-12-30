package com.example.myapp;

import com.example.myapp.exception.*;
import com.example.myapp.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BusinessExceptionHandler {

  @ExceptionHandler(DateParseException.class)
  protected ResponseEntity<ErrorResponse> DateParseExceptionHandler(DateParseException e) {
    final ErrorResponse response = new ErrorResponse(e.getErrorCode());
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(DecodedTokenParseException.class)
  protected ResponseEntity<ErrorResponse> DecodedTokenParseExceptionHandler(DecodedTokenParseException e) {
    final ErrorResponse response = new ErrorResponse(e.getErrorCode());
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(EmailSendException.class)
  protected ResponseEntity<ErrorResponse> EmailSendExceptionHandler(EmailSendException e) {
    final ErrorResponse response = new ErrorResponse(e.getErrorCode());
    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(ExceedMaximumCardNumberException.class)
  protected ResponseEntity<ErrorResponse> ExceedMaximumCardNumberExceptionHandler(ExceedMaximumCardNumberException e) {
    final ErrorResponse response = new ErrorResponse(e.getErrorCode());
    return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
  }

  @ExceptionHandler(InvalidEmailException.class)
  protected ResponseEntity<ErrorResponse> InvalidEmailExeptionHanlder(InvalidEmailException e) {
    final ErrorResponse response = new ErrorResponse(e.getErrorCode());
    return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(InvalidTokenException.class)
  protected ResponseEntity<ErrorResponse> InvalidTokenExceptionHandler(InvalidTokenException e) {
    final ErrorResponse response = new ErrorResponse(e.getErrorCode());
    return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
  }

  @ExceptionHandler(NotFoundException.class)
  protected ResponseEntity<ErrorResponse> NotFoundExceptionHandler(NotFoundException e) {
    final ErrorResponse response = new ErrorResponse(e.getErrorCode());
    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(TokenExpiredException.class)
  protected ResponseEntity<ErrorResponse> TokenExpiredExceptionHandler(TokenExpiredException e) {
    final ErrorResponse response = new ErrorResponse(e.getErrorCode());
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(UnauthorizedAccessException.class)
  protected ResponseEntity<ErrorResponse> UnauthorizedAccessExceptionHandler(UnauthorizedAccessException e) {
    final ErrorResponse response = new ErrorResponse(e.getErrorCode());
    return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(UploadImageException.class)
  protected ResponseEntity<ErrorResponse> UploadImageExceptionHandler(UploadImageException e) {
    final ErrorResponse response = new ErrorResponse(e.getErrorCode());
    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
  }

}
