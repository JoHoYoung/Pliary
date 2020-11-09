package com.example.myapp.context.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BaseResponse {
    String statusMessage;

    public BaseResponse(){
        this.statusMessage = "success";
    }
}
