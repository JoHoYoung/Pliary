package com.example.myapp.controller.attachment;

import com.example.myapp.context.request.attachment.Attachment;
import com.example.myapp.context.user.Session;
import com.example.myapp.factory.AttachmentMapperFactory;
import com.example.myapp.mapper.attachment.AttachmentMapper;
import com.example.myapp.model.attachment.AttachmentModel;
import com.example.myapp.response.BaseResponse;
import com.example.myapp.response.DataListResponse;
import com.example.myapp.service.ImageHandler;
import com.example.myapp.util.AwsS3Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/attachment")
public class AttachmentCrud {

  @Autowired
  AwsS3Util awsS3Util;

  @Autowired
  AttachmentMapperFactory attachmentMapperFactory;

  @Autowired
  ImageHandler imageHandler;


  @RequestMapping(value = "/create", method = RequestMethod.POST)
  public ResponseEntity<BaseResponse> createAttachment(@RequestAttribute("session")Session session
    ,@RequestParam("type")String type ,@RequestParam("userimage") List<MultipartFile> files)
    throws java.io.IOException {

    ArrayList<String> urls = imageHandler.uploadFile(type, session.getId(), files);
    final BaseResponse response = new DataListResponse(200, "success",urls);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @RequestMapping(value = "/read", method = RequestMethod.POST)
  public ResponseEntity<BaseResponse> readAttachment(@RequestBody Attachment param) {
    AttachmentMapper attachmentMapper = attachmentMapperFactory.getAttachmentMapper(param.getType());
    List<AttachmentModel> images = attachmentMapper.readAttachment(param.getId());

    final BaseResponse response = new DataListResponse<>(HttpStatus.OK.value(), "success", images);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @RequestMapping(value = "/delete", method = RequestMethod.POST)
  public ResponseEntity<BaseResponse> deleteAttachment(HttpServletRequest req, @RequestBody Attachment param) {

    AttachmentMapper attachmentMapper = attachmentMapperFactory.getAttachmentMapper(param.getType());
    attachmentMapper.deleteAttachment(param.getId());
    imageHandler.deleteFile(param.getFilename());

    final BaseResponse response = new BaseResponse(HttpStatus.OK.value(), "success");
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

}