package com.example.myapp.model;

import lombok.Data;

import java.util.Date;

@Data
public class Feed {

  private int id;
  private int cardId;
  private int overDegree;
  private String state;
  private Date feedAt;
  private Date createdAt;
  private Date updatedAt;

}
