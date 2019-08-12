package com.example.myapp.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectMapperSingleTon {

    private static ObjectMapper _instance;

    public static ObjectMapper getInstance(){
        if(_instance == null ){
            _instance = new ObjectMapper();
        }
        return _instance;
    }
    private ObjectMapperSingleTon(){}
}
