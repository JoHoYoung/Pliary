package com.example.myapp.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class DataResponse<T> extends BaseResponse {
  private T data;

  public DataResponse(int statusCode, String statusMsg, T data) {
    super(statusMsg);
    this.data = data;
  }
}
