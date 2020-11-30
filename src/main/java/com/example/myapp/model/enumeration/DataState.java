package com.example.myapp.model.enumeration;

public enum DataState {

    CREATED("data created"),
    TEMPORARY("temporal data"),
    DELETED("deleted data");

    private final String description;

    DataState(final String description){
        this.description = description;
    }

}