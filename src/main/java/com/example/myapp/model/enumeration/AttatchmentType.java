package com.example.myapp.model.enumeration;

public enum AttatchmentType {

    DIARY("diary"),
    PROFILE("profile"),
    CARD("card");

    private String description;
    AttatchmentType(String description){
        this.description = description;
    }

}
