package com.example.myapp.exception;

import com.example.myapp.ErrorCode;

public class UploadImageException extends BusinessException{
  public UploadImageException(ErrorCode errorCode){
    super(errorCode);
  }
}
