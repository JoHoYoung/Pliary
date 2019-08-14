package com.example.myapp.mapper.attachment;

import com.example.myapp.mapper.attachment.AttachmentMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
public interface DiaryAttachmentMapper extends AttachmentMapper {

    @Insert("INSERT INTO DIARYATTACHMENT(uid, diary_id, url, state,created_at, updated_at) VALUES(#{uid},#{diary_id},#{url}, 'C', now(),now())")
    public void createAttachment(@Param("uid")String uid, @Param("diary_id")String diary_id, @Param("url")String url);

}
