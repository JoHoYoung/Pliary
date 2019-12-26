package com.example.myapp.model;

import com.example.myapp.model.attachment.AttachmentModel;
import com.example.myapp.model.attachment.DiaryAttachmentModel;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class DiaryModel {
    String id;
    String cardId;
    String title;
    String body;
    private String state;
    Date createdAt;
    Date updatedAt;
    List<AttachmentModel> images;
}
