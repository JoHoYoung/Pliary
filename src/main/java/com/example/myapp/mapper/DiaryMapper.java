package com.example.myapp.mapper;

import com.example.myapp.model.DiaryModel;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface DiaryMapper {

  // 다이어리 등록
  @Insert("INSERT INTO DIARY(id, cardId, title, body, createdAt, updatedAt, state) " +
    "VALUES(#{id}, #{cardId}, #{title}, #{body}, now(), now(), 'C')")
  int createDiary(@Param("id") String id, @Param("cardId") String cardId, @Param("title") String title, @Param("body") String body);

  // 특정 카드(식물) 다이어리 읽기
  @Select("SELECT * FROM DIARY WHERE cardId=#{cardId} AND state='C'")
  @Results({@Result(property = "images", javaType = List.class, column = "id",
    many = @Many(select = "com.example.myapp.mapper.attachment.DiaryAttachmentMapper.readAttachment")), @Result(property = "id", column = "id")})
  ArrayList<DiaryModel> readAllDiary(@Param("cardId") String cardId);

  // 하나의 다이어리(선택) 읽기
  @Select("SELECT * FROM DIARY WHERE id = #{id} AND state='C'")
  @Results({@Result(property = "images", javaType = List.class, column = "id",
    many = @Many(select = "com.example.myapp.mapper.attachment.DiaryAttachmentMapper.readAttachment"))})
  DiaryModel readDiary(@Param("id") String id);

  // 다이어리 삭제 (1개 지정) - state = 'D' 로 update
  @Update("UPDATE DIARY SET state='D' WHERE id=#{id}")
  int deleteDiary(@Param("id") String id);

  // 다이어리 수정
  @Update("UPDATE DIARY SET title=#{title}, body=#{body}, updatedAt=now() WHERE id = #{id}")
  int updateDiary(@Param("id") String id, @Param("title") String title, @Param("body") String body);

  @Select("SELECT userId FROM CARD WHERE CARD.id = (SELECT cardId FROM DIARY WHERE id = #{id})")
  String getUserId(@Param("id") String id);

  @Update("UPDATE DIARY SET state='D' WHERE cardId=#{cardId}")
  int deleteDiaryFromCardId(@Param("cardId") String cardId);
}