package com.example.myapp.exception;

import com.example.myapp.ErrorCode;

public class DecodedTokenParseException extends BusinessException {
  public DecodedTokenParseException(ErrorCode errorCode){
    super(errorCode);
  }
}
