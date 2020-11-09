package com.example.myapp.model.enumeration;

public enum AuthType {

    KAKAO_OAUTH("kakao"),
    GOOGLE_OUATH("google"),
    APPLE_OAUTH("apple");

    private String description;
    AuthType(String description){
        this.description = description;
    }
}
