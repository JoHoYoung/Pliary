package com.example.myapp.exception;

import com.example.myapp.ErrorCode;

public class InvalidTokenException extends BusinessException{
  public InvalidTokenException(ErrorCode errorcode){
    super(errorcode);
  }
}
