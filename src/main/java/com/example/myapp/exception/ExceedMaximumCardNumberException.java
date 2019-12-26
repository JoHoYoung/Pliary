package com.example.myapp.exception;

import com.example.myapp.ErrorCode;

public class ExceedMaximumCardNumberException extends BusinessException {
  public ExceedMaximumCardNumberException(ErrorCode errorCode){
    super(errorCode);
  }
}
