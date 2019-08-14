package com.example.myapp.mapper.attachment;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Repository
public interface CardAttachmentMapper extends AttachmentMapper{

    @Insert("INSERT INTO CARDATTACHMENT(uid, card_id, url, state,created_at, updated_at) VALUES(#{uid},#{card_id},#{url}, 'C', now(),now())")
    public void createAttachment(@Param("uid")String uid, @Param("card_id")String card_id, @Param("url")String url);

}
