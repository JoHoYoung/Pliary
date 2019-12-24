package com.example.myapp.response;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
public class JwtResponse extends BaseResponse {

  private String accessToken;
  private String refreshToken;

  public JwtResponse(int statusCode, String statusMsg, String accessToken, String refreshToken){
    super(statusCode,statusMsg);
    this.accessToken = accessToken;
    this.refreshToken = refreshToken;
  }
}
