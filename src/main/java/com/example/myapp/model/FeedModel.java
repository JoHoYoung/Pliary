package com.example.myapp.model;

import lombok.Data;

import java.util.Date;

@Data
public class FeedModel {
    private String uid;
    private String card_id;
    private int over_degree;
    private String state;
    private Date created_at;
    private Date updated_at;
}
