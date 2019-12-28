package com.example.myapp.service;

import com.example.myapp.ErrorCode;
import com.example.myapp.exception.UploadImageException;
import com.example.myapp.factory.AttachmentMapperFactory;
import com.example.myapp.mapper.attachment.AttachmentMapper;
import com.example.myapp.util.AwsS3Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class ImageHandler {
  @Autowired
  AwsS3Util awsS3Util;

  @Autowired
  AttachmentMapperFactory attachmentMapperFactory;
  public ArrayList<String> uploadFile(String type, String upperId, List<MultipartFile> files) {
    ArrayList<String> images = new ArrayList<>();
    AttachmentMapper attachmentMapper = attachmentMapperFactory.getAttachmentMapper(type);
    try{
      for (int i = 0; i < files.size(); i++) {
        String id = UUID.randomUUID().toString();
        byte[] byteArr = files.get(i).getBytes();
        String filename = upperId + id.substring(0, 5);
        String url = awsS3Util.fileUpload("dailyissue", filename, byteArr);
        images.add(url);
        attachmentMapper.createAttachment(id, upperId, url, filename);
      }
      return images;
    }catch (IOException e){
      throw new UploadImageException(ErrorCode.FAIL_TO_UPLOAD_IMAGE);
    }
  }

  public void deleteFile(String filename){
    awsS3Util.fileDelete("dailyissue",filename);
  }
}
