package com.example.myapp.exception;

import com.example.myapp.ErrorCode;

public class TokenExpiredException extends BusinessException{
  public TokenExpiredException(ErrorCode errorcode){
    super(errorcode);
  }
}

