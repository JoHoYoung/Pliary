package com.example.myapp.exception;

import com.example.myapp.ErrorCode;

public class NotFoundException extends BusinessException {
  public NotFoundException(ErrorCode errorCode){
    super(errorCode);
  }
}
