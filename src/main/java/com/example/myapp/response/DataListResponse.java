package com.example.myapp.response;

import lombok.Data;

import java.util.List;

@Data
public class DataListResponse<T> extends BaseResponse {
  List<T> data;
  public DataListResponse(int statusCode, String statusMsg, List<T> data){
    super(statusCode,statusMsg);
    this.data = data;
  }
}