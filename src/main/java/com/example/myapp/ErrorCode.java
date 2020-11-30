package com.example.myapp;

public enum ErrorCode {

  // JWT Token
  JWT_TOKEN_EXPIRED(400,"A001","Token Expired"),
  INVALID_TOKEN(401,"A002","Invalid Token"),
  EMPTY_TOKEN(402,"A003","Empty Token"),
  DECODED_TOKEN_PARSE_ERROR(431,"A004","Error at Parse decoded Token"),

  // Email Error
  INVALID_EMAIL(410,"B001","InvalidEmail"),
  DUPLICATED_EMAIL(411,"B002","Duplicated Email"),
  EMAIL_SEND_ERROR(412,"E001","Fail To Send Email"),

  // Oauth Error
  USER_ALREADY_SIGNUP(413,"E002","User Already Sign up"),
  USER_NOT_EXIST(414,"E003", "User is not exist"),
  DUPICATE_OAUTH_TOKEN(415, "E004", "User's Oauth key is duplicated"),
  // image
  FAIL_TO_UPLOAD_IMAGE(421,"I001","Fail To Upload Images"),
  // Create Card
  EXCEED_MAX_CARD_NUMBER(441,"C001","Exceed Maximum Card Number"),

  // Date parsing
  DATE_PARSE_ERROR(471,"I001", "Date Parse Error"),

  // Data Access
  DATA_ACCESS_UNAUTHORIZED(481,"D001","UnAuthorized Data Access"),
  DATA_NOT_FOUND(481,"D002","DATA NOT FOUND");

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
