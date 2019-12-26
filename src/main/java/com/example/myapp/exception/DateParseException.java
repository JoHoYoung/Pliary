package com.example.myapp.exception;

import com.example.myapp.ErrorCode;

public class DateParseException extends BusinessException {
  public DateParseException(ErrorCode errorCode){
    super(errorCode);
  }
}
