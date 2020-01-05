package com.example.myapp.model.attachment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiaryAttachmentModel extends AttachmentModel {

  private int id;
  private int diaryId;
  private String url;
  private String state;
  private String filename;
  private Date createdAt;
  private Date updatedAt;

}
