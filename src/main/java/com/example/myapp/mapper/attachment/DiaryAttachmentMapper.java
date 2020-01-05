package com.example.myapp.mapper.attachment;

import com.example.myapp.mapper.attachment.AttachmentMapper;
import com.example.myapp.model.attachment.AttachmentModel;
import com.example.myapp.model.attachment.CardAttachmentModel;
import com.example.myapp.model.attachment.DiaryAttachmentModel;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface DiaryAttachmentMapper extends AttachmentMapper {

  @Insert("INSERT INTO DIARYATTACHMENT(diaryId, url, filename, state, createdAt, updatedAt) VALUES(#{diaryId},#{url}, #{filename},'C', now(),now())")
  void createAttachment(@Param("diaryId") int diaryId, @Param("url") String url, @Param("filename") String filename);

  @Update("UPDATE DIARYATTACHMENT SET state = 'D' WHERE  id = #{id}")
  void deleteAttachment(@Param("id") int id);

  @Select("SELECT * FROM DIARYATTACHMENT WHERE diaryId = #{diaryId} AND state='C'")
  @Results({@Result(property = "diaryId", column = "diaryId")})
  List<AttachmentModel> readAttachment(@Param("diaryId") int diaryId);

  @Select("SELECT userId from DIARY WHERE id = (SELECT diaryId FROM DIARYATTACHMENT WHERE id = #{id})")
  int getUserId(@Param("id") int id);
}