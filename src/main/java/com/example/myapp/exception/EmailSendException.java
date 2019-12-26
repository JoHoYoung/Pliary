package com.example.myapp.exception;

import com.example.myapp.ErrorCode;

import javax.mail.MessagingException;

public class EmailSendException extends BusinessException {
  public EmailSendException(ErrorCode errorCode){
    super(errorCode);
  }
}
