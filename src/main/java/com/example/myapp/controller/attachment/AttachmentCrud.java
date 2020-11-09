package com.example.myapp.controller.attachment;

import com.example.myapp.context.request.attachment.Attachment;
import com.example.myapp.context.user.Session;
import com.example.myapp.factory.AttachmentMapperFactory;
import com.example.myapp.mapper.attachment.AttachmentMapper;
import com.example.myapp.model.attachment.AttachmentModel;
import com.example.myapp.response.BaseResponse;
import com.example.myapp.response.DataListResponse;
import com.example.myapp.service.ImageService;
import com.example.myapp.service.AwsS3Service;
import com.example.myapp.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/attachment")
public class AttachmentCrud {

  @Autowired
  AwsS3Service awsS3Service;

  @Autowired
  AttachmentMapperFactory attachmentMapperFactory;

  @Autowired
  ImageService imageHandler;

  // 사진파일 생성
  @RequestMapping(value = "/create", method = RequestMethod.POST)
  public ResponseEntity<BaseResponse> createAttachment(@RequestAttribute("session") Session session
    , @RequestParam("type") String type, @RequestParam("id")int id, @RequestParam("userimage") List<MultipartFile> files) {

    // 자신의 데이터에 접근하는 지
    Util.numberDataAthorization(attachmentMapperFactory.getAttachmentMapper(type).getUserId(id), session.getId());

    // S3에 업로드 후, url 받아 옴
    ArrayList<String> urls = imageHandler.uploadFile(type, id, files);

    final BaseResponse response = new DataListResponse(200, "success", urls);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @RequestMapping(value = "/read", method = RequestMethod.POST)
  public ResponseEntity<BaseResponse> readAttachment(@RequestAttribute("session") Session session, @RequestBody Attachment param) {

    // 자신의 데이터에 접근하는 지
    Util.numberDataAthorization(attachmentMapperFactory.getAttachmentMapper(param.getType()).getUserId(param.getId()), session.getId());

    AttachmentMapper attachmentMapper = attachmentMapperFactory.getAttachmentMapper(param.getType());
    List<AttachmentModel> images = attachmentMapper.readAttachment(param.getId());

    int userId = attachmentMapper.getUserId(param.getId());

    Util.numberDataAthorization(userId, session.getId());

    final BaseResponse response = new DataListResponse<>(HttpStatus.OK.value(), "success", images);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @RequestMapping(value = "/delete", method = RequestMethod.POST)
  public ResponseEntity<BaseResponse> deleteAttachment(@RequestAttribute("session") Session session, @RequestBody Attachment param) {

    AttachmentMapper attachmentMapper = attachmentMapperFactory.getAttachmentMapper(param.getType());
    attachmentMapper.deleteAttachment(param.getId());

    int userId = attachmentMapper.getUserId(param.getId());
    Util.numberDataAthorization(userId, session.getId());
    imageHandler.deleteFile(param.getFilename());

    final BaseResponse response = new BaseResponse(HttpStatus.OK.value(), "success");
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

}