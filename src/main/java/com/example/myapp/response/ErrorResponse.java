package com.example.myapp.response;

import com.example.myapp.ErrorCode;
import lombok.Data;
import lombok.ToString;

@Data
public class ErrorResponse extends BaseResponse{
  private String code;
  public ErrorResponse(ErrorCode errorCode){
    super(errorCode.getStatus(),errorCode.getStatusMsg());
    this.code = errorCode.getStatusCode();
  }
}
