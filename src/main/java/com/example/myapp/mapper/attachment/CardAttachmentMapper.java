package com.example.myapp.mapper.attachment;

import com.example.myapp.model.attachment.AttachmentModel;
import com.example.myapp.model.attachment.CardAttachmentModel;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardAttachmentMapper extends AttachmentMapper{

    @Insert("INSERT INTO CARDATTACHMENT(uid, card_id, url, filename, state, created_at, updated_at) VALUES(#{uid}, #{card_id}, #{url}, #{filename}, 'C', now(),now())")
    void createAttachment(@Param("uid")String uid, @Param("card_id")String card_id, @Param("url")String url, @Param("filename")String filename);

    @Update("UPDATE CARDATTACHMENT SET state = 'D' WHERE  uid = #{uid}")
    void deleteAttachment(@Param("uid")String uid);

    @Select("SELECT * FROM CARDATTACHMENT WHERE card_id = #{card_id} AND state='C'")
    @Results({@Result(property = "card_id", column = "card_id")})
    List<AttachmentModel> readAttachment(@Param("card_id")String card_id);
}