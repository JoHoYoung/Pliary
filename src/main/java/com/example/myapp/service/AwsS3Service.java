package com.example.myapp.service;

import java.io.ByteArrayInputStream;
import java.io.File;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
@AllArgsConstructor
public class AwsS3Service {

  @Autowired
  private AmazonS3 amazonS3;

  // 파일 업로드
  public String fileUpload(String bucketName, String fileName, byte[] fileData){
    String filePath = (fileName).replace(File.separatorChar, '/'); // 파일 구별자를 `/`로 설정(\->/) 이게 기존에 / 였어도 넘어오면서 \로 바뀌는 거같다.
    ObjectMetadata metaData = new ObjectMetadata();
    metaData.setContentLength(fileData.length);   //메타데이터 설정 --> 원래는 128kB까지 업로드 가능했으나 파일크기만큼 버퍼를 설정시켰다.
    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(fileData); //파일 넣음
    amazonS3.putObject(bucketName, filePath, byteArrayInputStream, metaData);
    String imgName = (fileName).replace(File.separatorChar, '/');
    return amazonS3.generatePresignedUrl(new GeneratePresignedUrlRequest(bucketName, imgName)).toString();
  }

  // 파일 삭제
  public void fileDelete(String bucketName, String fileName) {
    String imgName = (fileName).replace(File.separatorChar, '/');
    amazonS3.deleteObject(bucketName, imgName);
  }

  // 파일 URL
  public String getFileURL(String bucketName, String fileName) {
    String imgName = (fileName).replace(File.separatorChar, '/');
    return amazonS3.generatePresignedUrl(new GeneratePresignedUrlRequest(bucketName, imgName)).toString();
  }

}