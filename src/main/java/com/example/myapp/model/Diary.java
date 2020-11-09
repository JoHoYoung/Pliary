package com.example.myapp.model;

import lombok.Data;

import java.util.Date;

@Data
public class Diary {

  private Integer id;
  private Integer cardId;
  private String title;
  private String body;
  private String state;
  private Date createdAt;
  private Date updatedAt;

}
