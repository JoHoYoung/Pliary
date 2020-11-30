package com.example.myapp.context.response;

public class DataResponse<T> extends BaseResponse {
    public T data;

    public DataResponse(T data){
        this.data = data;
    }

    public DataResponse(String statusMsg, T data) {
        super(statusMsg);
        this.data = data;
    }
}
