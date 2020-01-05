package com.example.myapp.mapper.attachment;

import com.example.myapp.model.attachment.AttachmentModel;
import com.example.myapp.model.attachment.CardAttachmentModel;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardAttachmentMapper extends AttachmentMapper {

  @Insert("INSERT INTO CARDATTACHMENT(cardId, url, filename, state, createdAt, updatedAt) VALUES(#{cardId}, #{url}, #{filename}, 'C', now(),now())")
  void createAttachment(@Param("cardId") int cardId, @Param("url") String url, @Param("filename") String filename);

  @Update("UPDATE CARDATTACHMENT SET state = 'D' WHERE id = #{id}")
  void deleteAttachment(@Param("id") int id);

  @Select("SELECT * FROM CARDATTACHMENT WHERE cardId = #{cardId} AND state='C'")
  @Results({@Result(property = "cardId", column = "cardId")})
  List<AttachmentModel> readAttachment(@Param("cardId")int cardId);

  @Select("SELECT userId FROM CARDATTACHMENT WHERE id = #{id}")
  int getUserId(@Param("id")int id);


}