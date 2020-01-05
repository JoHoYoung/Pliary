package com.example.myapp.exception;

import com.example.myapp.ErrorCode;

public class InvalidAuthException extends BusinessException {
  public InvalidAuthException(ErrorCode errorCode){
    super(errorCode);
  }
}
