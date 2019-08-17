package com.example.myapp.mapper.attachment;

import com.example.myapp.mapper.attachment.AttachmentMapper;
import com.example.myapp.model.attachment.AttachmentModel;
import com.example.myapp.model.attachment.CardAttachmentModel;
import com.example.myapp.model.attachment.DiaryAttachmentModel;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ProfileAttachmentMapper extends AttachmentMapper {

    @Insert("INSERT INTO PROFILEATTACHMENT(uid, user_id, url, filename, state, created_at, updated_at) VALUES(#{uid},#{user_id},#{url}, #{filename},'C', now(),now())")
    void createAttachment(@Param("uid")String uid, @Param("user_id")String diary_id, @Param("url")String url, @Param("filename")String filename);

    @Update("UPDATE PROFILEATTACHMENT SET state = 'D' WHERE  uid = #{uid}")
    void deleteAttachment(@Param("uid")String uid);

    @Select("SELECT * FROM PROFILEATTACHMENT WHERE user_id = #{user_id} AND state='C'")
    @Results({@Result(property = "user_id", column = "user_id")})
    List<AttachmentModel> readAttachment(@Param("user_id")String diary_id);
}