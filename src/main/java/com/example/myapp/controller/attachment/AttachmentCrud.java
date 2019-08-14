package com.example.myapp.controller.attachment;

import com.example.myapp.context.attachment.Attachment;
import com.example.myapp.factory.AttachmentMapperFactory;
import com.example.myapp.factory.AttachmentModelFactory;
import com.example.myapp.mapper.attachment.AttachmentMapper;
import com.example.myapp.model.attachment.AttachmentModel;
import com.example.myapp.util.AwsS3Util;
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

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public JSONObject createAttachment(HttpServletRequest req, @RequestParam("userimage") List<MultipartFile> files) throws java.io.IOException{
        JSONObject JSON = new JSONObject();
        JSONObject sesssion = (JSONObject) req.getAttribute("session");
        System.out.println("LL");
        String type = req.getParameter("type");
        String fileName = req.getParameter("filename");
        String upperId = req.getParameter("uid");
        System.out.println(upperId);
        AttachmentMapper attachmentMapper = attachmentMapperFactory.getAttachmentMapper(type);
        try{
            ArrayList<String> urls = new ArrayList<>();
            for(int i = 0;i<files.size();i++){
                String uid = UUID.randomUUID().toString();
                byte [] byteArr=files.get(i).getBytes();
                String url = awsS3Util.fileUpload("groot.devdogs.kr", fileName, byteArr);
                System.out.println(url);
                urls.add(url);
                attachmentMapper.createAttachment(uid, upperId, url);
            }
            System.out.println(urls);

            System.out.println("LL1");
            JSON.put("statusCode", 200);
            JSON.put("statusMsg", "success");
            JSON.put("data",urls);
            return JSON;
        }catch(Exception e){
            System.out.println(e);
            JSON.put("statusCode", 200);
            JSON.put("statusMsg", "success");
            return JSON;
        }
    }
}
