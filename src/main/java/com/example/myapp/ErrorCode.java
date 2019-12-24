package com.example.myapp;

public enum ErrorCode {
  JWT_TOKEN_EXPIRED(400,"A001","Token Expired"),
  INVALID_TOKEN(401,"A002","Invalid Token"),
  EMPTY_TOKEN(402,"A003","Empty Token"),

  INVALID_EMAIL(410,"B001","InvalidEmail"),
  DUPLICATED_EMAIL(411,"B002","Duplicate Email"),

  INVALID_INPUT_VALUE(400, "C001", " Invalid Input Value"),
  METHOD_NOT_ALLOWED(405, "C002", " Invalid Input Value"),
  HANDLE_ACCESS_DENIED(403, "C006", "Access is Denied"),

  // Member
  EMAIL_DUPLICATION(400, "M001", "Email is Duplication"),
  LOGIN_INPUT_INVALID(400, "M002", "Login input is invalid");

  private final String statusCode;
  private final String statusMsg;
  private int status;

  ErrorCode(final int status, final String code, final String message) {
    this.status = status;
    this.statusMsg = message;
    this.statusCode = code;
  }

  public int getStatus(){
    return this.status;
  }
  public String getStatusCode(){
    return this.statusCode;
  }
  public String getStatusMsg(){
    return this.statusMsg;
  }
}
