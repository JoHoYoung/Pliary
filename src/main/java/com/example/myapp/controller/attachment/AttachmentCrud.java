package com.example.myapp.controller.attachment;

import com.example.myapp.context.attachment.Attachment;
import com.example.myapp.factory.AttachmentMapperFactory;
import com.example.myapp.factory.AttachmentModelFactory;
import com.example.myapp.mapper.attachment.AttachmentMapper;
import com.example.myapp.model.attachment.AttachmentModel;
import com.example.myapp.util.AwsS3Util;
import com.example.myapp.util.ObjectMapperSingleTon;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/attachment")
public class AttachmentCrud {

    @Autowired
    AwsS3Util awsS3Util;

    @Autowired
    AttachmentMapperFactory attachmentMapperFactory;

    ObjectMapper objectMapper = ObjectMapperSingleTon.getInstance();

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public JSONObject createAttachment(HttpServletRequest req, @RequestParam("userimage") List<MultipartFile> files) throws java.io.IOException{
        JSONObject JSON = new JSONObject();
        JSONObject sesssion = (JSONObject) req.getAttribute("session");
        String email = (String)sesssion.get("email");
        String type = req.getParameter("type");
        String upperId = req.getParameter("uid");
        System.out.println(upperId);
        AttachmentMapper attachmentMapper = attachmentMapperFactory.getAttachmentMapper(type);
        JSONObject images = new JSONObject();
        try{
            for(int i = 0;i<files.size();i++){
                String uid = UUID.randomUUID().toString();
                byte [] byteArr=files.get(i).getBytes();
                String url = awsS3Util.fileUpload("groot.devdogs.kr",uid, byteArr);
                String filename = email + uid.substring(0,5);
                images.put(uid,url);
                attachmentMapper.createAttachment(uid, upperId, url, filename);
            }
            JSON.put("statusCode", 200);
            JSON.put("statusMsg", "success");
            JSON.put("data", images);
            return JSON;
        }catch(Exception e){
            JSON.put("statusCode", 200);
            JSON.put("statusMsg", "success");
            return JSON;
        }
    }

    @RequestMapping(value = "/read", method = RequestMethod.POST)
    public JSONObject readAttachment(HttpServletRequest req, @RequestBody Attachment param) {
        JSONObject JSON = new JSONObject();
        JSONObject sesssion = (JSONObject) req.getAttribute("session");
        try{
            String type = param.getType();
            String uid = param.getUid();
            AttachmentMapper attachmentMapper = attachmentMapperFactory.getAttachmentMapper(type);
            List<AttachmentModel> images = attachmentMapper.readAttachment(uid);
            List<String> data = new ArrayList<>();
            for (int i = 0; i < images.size(); i++) {
                data.add(objectMapper.writeValueAsString(images.get(i)));
            }
            JSON.put("statusCode", 200);
            JSON.put("statusMsg", "success");
            JSON.put("data",data);
            return JSON;
        }catch(Exception e){
            System.out.println(e);
            JSON.put("statusCode", 200);
            JSON.put("statusMsg", "success");
            return JSON;
        }
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public JSONObject deleteAttachment(HttpServletRequest req, @RequestBody Attachment param) {
        JSONObject JSON = new JSONObject();
        JSONObject sesssion = (JSONObject) req.getAttribute("session");
        try{
            String type = param.getType();
            String uid = param.getUid();
            AttachmentMapper attachmentMapper = attachmentMapperFactory.getAttachmentMapper(type);
            attachmentMapper.deleteAttachment(uid);
            JSON.put("statusCode", 200);
            JSON.put("statusMsg", "success");
            return JSON;
        }catch(Exception e){
            JSON.put("statusCode", 200);
            JSON.put("statusMsg", "success");
            return JSON;
        }
    }


}
