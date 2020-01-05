package com.example.myapp.model;

import lombok.Data;

import java.util.Date;

@Data
public class TypeModel {

  private int id;
  private String krName;
  private String engName;
  private Date createdAt;
  private Date updatedAt;

}
