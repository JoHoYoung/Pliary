package com.example.myapp.mapper.attachment;

import com.example.myapp.mapper.attachment.AttachmentMapper;
import com.example.myapp.model.attachment.AttachmentModel;
import com.example.myapp.model.attachment.CardAttachmentModel;
import com.example.myapp.model.attachment.DiaryAttachmentModel;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface DiaryAttachmentMapper extends AttachmentMapper {

    @Insert("INSERT INTO DIARYATTACHMENT(uid, diary_id, url, filename, state, created_at, updated_at) VALUES(#{uid},#{diary_id},#{url}, #{filename},'C', now(),now())")
    void createAttachment(@Param("uid")String uid, @Param("diary_id")String diary_id, @Param("url")String url, @Param("filename")String filename);

    @Update("UPDATE DIARYATTACHMENT SET state = 'D' WHERE  uid = #{uid}")
    void deleteAttachment(@Param("uid")String uid);

    @Select("SELECT * FROM DIARYATTACHMENT WHERE diary_id = #{diary_id}")
    List<AttachmentModel> readAttachment(@Param("diary_id")String diary_id);
}
