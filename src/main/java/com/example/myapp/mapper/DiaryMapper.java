package com.example.myapp.mapper;

import com.example.myapp.model.DiaryModel;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface DiaryMapper {

    // 다이어리 등록
    @Insert("INSERT INTO DIARY(uid, card_id, title, body, created_at, updated_at, state) " +
            "VALUES(#{uid}, #{card_id}, #{title}, #{body}, now(), now(), 'C')")
    int createDiary(@Param("uid")String uid, @Param("card_id")String card_id, @Param("title")String title, @Param("body")String body);

    // 특정 카드(식물) 다이어리 읽기
    @Select("SELECT * FROM DIARY WHERE card_id=#{card_id} AND state='C'")
    @Results({@Result(property = "images", javaType = List.class, column = "uid",
            many = @Many(select = "com.example.myapp.mapper.attachment.DiaryAttachmentMapper.readAttachment")),@Result(property="uid",column = "uid")})
    ArrayList<DiaryModel> readAllDiary(@Param("card_id")String card_id);

    // 하나의 다이어리(선택) 읽기
    @Select("SELECT * FROM DIARY WHERE uid = #{uid} AND state='C'")
    @Results({@Result(property = "images", javaType = List.class, column = "uid",
            many = @Many(select = "com.example.myapp.mapper.attachment.DiaryAttachmentMapper.readAttachment"))})
    DiaryModel readDiary(@Param("uid")String uid);

    // 다이어리 삭제 (1개 지정) - state = 'D' 로 update
    @Update("UPDATE DIARY SET state='D' WHERE uid=#{uid}")
    int deleteDiary(@Param("uid")String uid);

    // 다이어리 수정
    @Update("UPDATE DIARY SET title=#{title}, body=#{body}, updated_at=now() WHERE uid = #{uid}")
    int updateDiary(@Param("uid")String uid, @Param("title")String title, @Param("body")String body);
}