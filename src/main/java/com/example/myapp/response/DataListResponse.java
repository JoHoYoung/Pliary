package com.example.myapp.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataListResponse<T> extends BaseResponse {
  List<T> data;

  public DataListResponse(int statusCode, String statusMsg, List<T> data) {
    super(statusCode, statusMsg);
    this.data = data;
  }
}