package com.example.myapp.model;

import lombok.Data;

import java.util.Date;

@Data
public class Feed {

  private Integer id;
  private Integer cardId;
  private Integer overDegree;
  private String state;
  private Date feedAt;
  private Date createdAt;
  private Date updatedAt;

}
