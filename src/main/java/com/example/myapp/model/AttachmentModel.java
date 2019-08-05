package com.example.myapp.model;

import lombok.Data;

import java.util.Date;

@Data
public class AttachmentModel {
    private String uid;
    private String card_id;
    private String url;
    private Date created_at;
    private Date updated_at;
}
