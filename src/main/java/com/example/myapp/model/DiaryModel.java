package com.example.myapp.model;

import lombok.Data;

import java.util.Date;

@Data
public class DiaryModel {
    private String uid;
    private String card_id;
    private char state;
    private String body;
    private Date created_at;
    private Date updated_at;
}