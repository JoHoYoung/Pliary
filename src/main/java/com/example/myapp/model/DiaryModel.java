package com.example.myapp.model;

import lombok.Data;

import java.util.Date;

@Data
public class DiaryModel {
    private String uid;
    private String card_id;
    private char state;
    private String maintext;
    private Date started_date;
    private Date updated_date;
}