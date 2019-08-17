package com.example.myapp.model;

import com.example.myapp.model.attachment.CardAttachmentModel;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class CardModel {
    private String uid;
    private String user_id;
    private String name;
    private String nickname;
    private String state;
    int init_period;
    int now_period;
    private Date created_at;
    private Date updated_at;
    List<CardAttachmentModel> images;

}
