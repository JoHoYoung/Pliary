package com.example.myapp.model.attachment;

import lombok.Data;

import java.util.Date;

@Data
public class CardAttachmentModel extends AttachmentModel {

  private int id;
  private int cardId;
  private String url;
  private String state;
  private String filename;
  private Date createdAt;
  private Date updatedAt;

}
