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

  public DataListResponse(String statusMsg, List<T> data) {
    super(statusMsg);
    this.data = data;
  }
}