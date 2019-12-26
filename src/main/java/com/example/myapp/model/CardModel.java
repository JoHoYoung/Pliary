package com.example.myapp.model;

import com.example.myapp.model.attachment.CardAttachmentModel;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class CardModel {
  private String id;
  private String userId;
  private String name;
  private String nickname;
  private String state;
  int initPeriod;
  int nowPeriod;
  private Date createdAt;
  private Date updatedAt;
  List<CardAttachmentModel> images;
}
