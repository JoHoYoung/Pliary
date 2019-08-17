package com.example.myapp.mapper.attachment;

import com.example.myapp.model.attachment.AttachmentModel;
import com.example.myapp.model.attachment.DiaryAttachmentModel;

import java.util.List;

public interface AttachmentMapper {

    void createAttachment(String uid,String upperId, String url,String filename);
    void deleteAttachment(String uid);
    List<AttachmentModel> readAttachment(String id);
}
