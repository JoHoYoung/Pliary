package com.example.myapp.model;

import com.example.myapp.model.attachment.AttachmentModel;
import com.example.myapp.model.attachment.DiaryAttachmentModel;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Diary {

  private int id;
  private int cardId;
  private String title;
  private String body;
  private String state;
  private Date createdAt;
  private Date updatedAt;
  private List<AttachmentModel> images;

}
