package com.example.myapp.model;

import com.example.myapp.context.request.attachment.Attachment;
import lombok.*;
import org.apache.ibatis.type.Alias;

import java.util.Date;
import java.util.List;

@Data
@Alias("USER")
public class UserModel {

  private int id;
  private String email;
  private String state;
  private String name;
  private String nickname;
  private String birth;
  private String phone;
  private String token;
  private int provider;
  private Date createdAt;
  private Date updatedAt;
  private List<Attachment> images;

}