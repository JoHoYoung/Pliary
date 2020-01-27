package com.example.myapp.factory;

import com.example.myapp.mapper.attachment.AttachmentMapper;
import com.example.myapp.mapper.attachment.CardAttachmentMapper;
import com.example.myapp.mapper.attachment.DiaryAttachmentMapper;
import com.example.myapp.mapper.attachment.ProfileAttachmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AttachmentMapperFactory {

  @Autowired
  CardAttachmentMapper cardAttachmentMapper;

  @Autowired
  DiaryAttachmentMapper diaryAttachmentMapper;

  @Autowired
  ProfileAttachmentMapper profileAttachmentMapper;

  public AttachmentMapper getAttachmentMapper(String type) {
    if (type.equals("card")) {
      return cardAttachmentMapper;
    }
    if (type.equals("diary")) {
      return diaryAttachmentMapper;
    }
    if (type.equals("profile")) {
      return profileAttachmentMapper;
    }
    return null;
  }
}
