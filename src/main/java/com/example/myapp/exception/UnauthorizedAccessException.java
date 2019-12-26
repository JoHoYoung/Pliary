package com.example.myapp.exception;

import com.example.myapp.ErrorCode;

public class UnauthorizedAccessException extends BusinessException{
  public UnauthorizedAccessException(ErrorCode errorCode){
    super(errorCode);
  }
}
