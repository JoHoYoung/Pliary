package com.example.myapp.model.attachment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiaryAttachmentModel extends AttachmentModel {
    private String uid;
    private String diary_id;
    private String url;
    private String state;
    private String filename;
    private Date created_at;
    private Date updated_at;
}
