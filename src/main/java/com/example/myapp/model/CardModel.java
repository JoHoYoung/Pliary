package com.example.myapp.model;

import com.example.myapp.model.attachment.CardAttachmentModel;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CardModel {

  private int id;
  private int userId;
  private int typeId;
  private String name;
  private String nickName;
  private String engName;
  private String krName;
  private String state;

  private int waterPeriod;
  private int remainPeriod;

  private Date createdAt;
  private Date updatedAt;

  List<CardAttachmentModel> images;
}
