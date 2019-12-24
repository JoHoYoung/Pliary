package com.example.myapp.controller.attachment;

import com.example.myapp.context.attachment.Attachment;
import com.example.myapp.factory.AttachmentMapperFactory;
import com.example.myapp.mapper.attachment.AttachmentMapper;
import com.example.myapp.model.attachment.AttachmentModel;
import com.example.myapp.response.BaseResponse;
import com.example.myapp.response.DataResponse;
import com.example.myapp.service.ImageHandler;
import com.example.myapp.util.AwsS3Util;
import com.example.myapp.util.ObjectMapperSingleTon;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
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
  public ResponseEntity<BaseResponse> createAttachment(HttpServletRequest req, @RequestParam("userimage") List<MultipartFile> files) throws java.io.IOException {

    JSONObject sesssion = (JSONObject) req.getAttribute("session");
    ArrayList<String> urls = imageHandler.uploadFile(req.getParameter("type"), (String) sesssion.get("email"), req.getParameter("uid"), files);

    final BaseResponse response = new DataResponse(200, "success",urls);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @RequestMapping(value = "/read", method = RequestMethod.POST)
  public ResponseEntity<BaseResponse> readAttachment(@RequestBody Attachment param) {
    AttachmentMapper attachmentMapper = attachmentMapperFactory.getAttachmentMapper(param.getType());
    List<AttachmentModel> images = attachmentMapper.readAttachment(param.getUid());

    final BaseResponse response = new DataResponse<>(HttpStatus.OK.value(), "success", images);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @RequestMapping(value = "/delete", method = RequestMethod.POST)
  public ResponseEntity<BaseResponse> deleteAttachment(HttpServletRequest req, @RequestBody Attachment param) {

    AttachmentMapper attachmentMapper = attachmentMapperFactory.getAttachmentMapper(param.getType());
    attachmentMapper.deleteAttachment(param.getUid());
    imageHandler.deleteFile(param.getFilename());

    final BaseResponse response = new BaseResponse(HttpStatus.OK.value(), "success");
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

}