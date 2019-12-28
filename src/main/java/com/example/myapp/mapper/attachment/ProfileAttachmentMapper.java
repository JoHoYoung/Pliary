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

  @Insert("INSERT INTO PROFILEATTACHMENT(id, userId, url, filename, state, createdAt, updatedAt) VALUES(#{id},#{userId},#{url}, #{filename},'C', now(),now())")
  void createAttachment(@Param("id") String id, @Param("userId") String diaryId, @Param("url") String url, @Param("filename") String filename);

  @Update("UPDATE PROFILEATTACHMENT SET state = 'D' WHERE id = #{id}")
  void deleteAttachment(@Param("id") String id);

  @Select("SELECT * FROM PROFILEATTACHMENT WHERE userId = #{userId} AND state='C'")
  @Results({@Result(property = "userId", column = "userId")})
  List<AttachmentModel> readAttachment(@Param("userId") String userId);

  @Select("SELECT userId FROM PROFILEATTACHMENT WHERE id = #{id}")
  String getUserId(@Param("id") String id);

}