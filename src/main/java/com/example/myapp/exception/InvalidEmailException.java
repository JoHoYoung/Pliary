package com.example.myapp.exception;

import com.example.myapp.ErrorCode;

public class InvalidEmailException extends BusinessException {

  public InvalidEmailException(ErrorCode errorCode){
    super(errorCode);
  }

}
