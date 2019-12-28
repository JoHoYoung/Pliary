package com.example.myapp.mapper;

import com.example.myapp.model.CardModel;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardMapper {

  // 식물 등록
  @Insert("INSERT INTO CARD(id, userId, name, nickName, initPeriod, nowPeriod, state, createdAt, updatedAt) " +
    "VALUES(#{id}, #{userId}, #{name}, #{nickName}, #{initPeriod}, #{initPeriod}, 'C', now(), now())")
  int createCard(@Param("id") String id, @Param("userId") String userId, @Param("name") String name, @Param("nickName") String nickName, @Param("initPeriod") int initPeriod);

  // 특정 사용자 식물 전체 목록 select
  @Select("SELECT * FROM CARD WHERE userId = #{userId} AND state = 'C'")
  @Results({@Result(property = "images", javaType = List.class, column = "id",
    many = @Many(select = "com.example.myapp.mapper.attachment.CardAttachmentMapper.readAttachment")), @Result(property = "id", column = "id")})
  List<CardModel> readAllCard(@Param("userId") String userId);

  @Select("SELECT userId FROM CARD WHERE id = #{id}")
  String getUserId(@Param("id") String id);

  @Select("SELECT * FROM CARD WHERE state = 'C' AND id=#{id}")
  CardModel readCard(@Param("id") String id);

  // 식물 카드 개수 제한을 위한 user_id count (user_id가 6이상이면 CreateCard 가 제한됨)
  @Select("SELECT COUNT(*) from CARD WHERE userId = #{userId} AND state='C'")
  int countCard(@Param("userId") String userId);

  @Update("UPDATE CARD SET state = 'D' WHERE id=#{id}")
  void deleteCard(@Param("id") String id);

  @Update("UPDATE CARD SET name=#{name}, nickName=#{nickName}, initPeriod=#{initPeriod}, nowPeriod=#{nowPeriod}, updatedAt = now() WHERE id=#{id} AND state ='C'")
  void updateCard(@Param("id") String id, @Param("name") String name, @Param("nickName") String nickName, @Param("initPeriod") int init_period, @Param("nowPeriod") int nowPeriod);

}
