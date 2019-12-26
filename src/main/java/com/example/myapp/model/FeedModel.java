package com.example.myapp.model;

import lombok.Data;

import java.util.Date;

@Data
public class FeedModel {
    private String id;
    private String cardId;
    private int overDegree;
    private String state;
    private Date createdAt;
    private Date updatedAt;
}
