package com.example.myapp.exception;

import com.example.myapp.ErrorCode;

public abstract class BusinessException extends RuntimeException {
  private ErrorCode errorCode;
  private StringBuilder stringBuilder = new StringBuilder();

  public BusinessException(ErrorCode errorCode) {
    this.errorCode = errorCode;
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
    this.stringBuilder.append("ERROR | ");
    this.stringBuilder.append(this.getStatus());
    this.stringBuilder.append(" | ");
    this.stringBuilder.append(this.getStatusCode());
    this.stringBuilder.append(" | ");
    this.stringBuilder.append(this.getStatusMsg());
    return this.stringBuilder.toString();
  }
}
