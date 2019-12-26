package com.example.myapp.exception;

import com.example.myapp.ErrorCode;
import com.example.myapp.util.DateHelper;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BusinessException extends RuntimeException {
  private ErrorCode errorCode;
  private StringBuilder stringBuilder = new StringBuilder();

  @Autowired
  DateHelper dateHelper;

  public BusinessException(ErrorCode errorCode) {
    this.errorCode = errorCode;
  }

  public ErrorCode getErrorCode(){
    return this.errorCode;
  }

  public int getStatus() {
    return this.errorCode.getStatus();
  }

  public String getStatusCode() {
    return this.errorCode.getStatusCode();
  }

  public String getStatusMsg() {
    return this.errorCode.getStatusMsg();
  }

  public String toString() {
    this.stringBuilder.append(dateHelper.getDateToString());
    this.stringBuilder.append(" | ");
    this.stringBuilder.append("ERROR | ");
    this.stringBuilder.append(this.getStatus());
    this.stringBuilder.append(" | ");
    this.stringBuilder.append(this.getStatusCode());
    this.stringBuilder.append(" | ");
    this.stringBuilder.append(this.getStatusMsg());
    return this.stringBuilder.toString();
  }
}
