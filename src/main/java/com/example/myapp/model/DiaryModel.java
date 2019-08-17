package com.example.myapp.model;

import com.example.myapp.model.attachment.AttachmentModel;
import com.example.myapp.model.attachment.DiaryAttachmentModel;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class DiaryModel {
    String uid;
    String card_id;
    String title;
    String body;
    private String state;
    Date created_at;
    Date update_at;
    List<AttachmentModel> images;
}
