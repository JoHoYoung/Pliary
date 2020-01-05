package com.example.myapp.mapper.attachment;

import com.example.myapp.model.attachment.AttachmentModel;

import java.util.List;

public interface AttachmentMapper {

  void createAttachment(int upperId, String url, String filename);
  void deleteAttachment(int id);
  List<AttachmentModel> readAttachment(int id);
  int getUserId(int id);
}
