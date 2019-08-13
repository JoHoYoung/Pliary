package com.example.myapp.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Repository
public interface CardAttachmentMapper{

    @Insert("INSERT INTO CARDATTACHMENT(uid, card_id, url,crated_at, updated_at) VALUES(#{uid},#{card_id},now(),now())")
    public void createCardAttachment(@Param("uid")String uid, @Param("card_id")String card_id, @Param("url")String url);

}
