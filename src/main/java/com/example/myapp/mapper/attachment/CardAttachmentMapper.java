package com.example.myapp.mapper.attachment;

import com.example.myapp.model.attachment.AttachmentModel;
import com.example.myapp.model.attachment.CardAttachmentModel;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardAttachmentMapper extends AttachmentMapper{

    @Insert("INSERT INTO CARDATTACHMENT(id, cardId, url, filename, state, createdAt, updatedAt) VALUES(#{id}, #{cardId}, #{url}, #{filename}, 'C', now(),now())")
    void createAttachment(@Param("id")String id, @Param("cardId")String cardId, @Param("url")String url, @Param("filename")String filename);

    @Update("UPDATE CARDATTACHMENT SET state = 'D' WHERE id = #{id}")
    void deleteAttachment(@Param("id")String id);

    @Select("SELECT * FROM CARDATTACHMENT WHERE cardId = #{cardId} AND state='C'")
    @Results({@Result(property = "cardId", column = "cardId")})
    List<AttachmentModel> readAttachment(@Param("cardId")String cardId);
}