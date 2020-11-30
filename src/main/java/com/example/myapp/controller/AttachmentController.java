package com.example.myapp.controller;

import com.example.myapp.context.request.attachment.Attachment;
import com.example.myapp.context.user.Session;
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
public class AttachmentController {

}