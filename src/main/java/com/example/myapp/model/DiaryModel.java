package com.example.myapp.model;

import lombok.Data;

import java.util.Date;

@Data
public class DiaryModel {
    String uid;
    String card_id;
    String title;
    String body;
    private String state;
    Date created_at;
    Date update_at;
}
