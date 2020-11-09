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

    @Insert("INSERT INTO PROFILEATTACHMENT(userId, url, filename, state, createdAt, updatedAt) VALUES(#{userId}, #{url}, #{filename},'C', now(),now())")
    void createAttachment(@Param("userId") int userId, @Param("url") String url, @Param("filename") String filename);

    @Update("UPDATE PROFILEATTACHMENT SET state = 'D' WHERE id = #{id}")
    void deleteAttachment(@Param("id") int id);

    @Select("SELECT * FROM PROFILEATTACHMENT WHERE userId = #{userId} AND state='C'")
    @Results({@Result(property = "userId", column = "userId")})
    List<AttachmentModel> readAttachment(@Param("userId") int userId);

    @Select("SELECT userId FROM PROFILEATTACHMENT WHERE id = #{id}")
    int getUserId(@Param("id") int id);

}