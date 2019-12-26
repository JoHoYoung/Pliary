package com.example.myapp.model.attachment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttachmentModel {
    private String id;
    private String diaryId;
    private String cardId;
    private String userId;
    private String url;
    private String state;
    private String filename;
    private Date createdAt;
    private Date updatedAt;
}
