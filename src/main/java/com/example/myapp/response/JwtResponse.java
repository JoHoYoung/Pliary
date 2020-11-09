package com.example.myapp.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse extends BaseResponse {

  private String accessToken;
  private String refreshToken;

  public JwtResponse(String statusMsg, String accessToken, String refreshToken){
    super(statusMsg);
    this.accessToken = accessToken;
    this.refreshToken = refreshToken;
  }

}
