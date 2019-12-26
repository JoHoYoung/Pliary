package com.example.myapp.exception;

import com.example.myapp.ErrorCode;

public class DecodedTokenParseError extends BusinessException {
  public DecodedTokenParseError(ErrorCode errorCode){
    super(errorCode);
  }
}
