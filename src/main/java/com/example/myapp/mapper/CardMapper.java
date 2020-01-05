package com.example.myapp.mapper;

import com.example.myapp.model.CardModel;
import lombok.Data;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardMapper {

  // 식물 등록
  @Insert("INSERT INTO CARD(userId, typeId, name, nickname, engName, krName, state, waterPeriod, remainPeriod, createdAt, updatedAt) " +
    "VALUES(#{userId}, #{typeId}, #{name}, #{nickName}, #{engName}, #{krName}, 'C', #{waterPeriod}, #{remainPeriod}, now(), now())")
  int createCard(@Param("userId")int userId, @Param("typeId")int typeId, @Param("name")String name, @Param("nickName")String nickName, @Param("engName")String engName, @Param("krName")String krName
  , @Param("waterPeriod")int waterPeriod, @Param("remainPeriod")int remainPeriod);

  // 특정 사용자 식물 전체 목록 select
  @Select("SELECT * FROM CARD WHERE userId = #{userId} AND state = 'C'")
  @Results({@Result(property = "images", javaType = List.class, column = "id",
    many = @Many(select = "com.example.myapp.mapper.attachment.CardAttachmentMapper.readAttachment")), @Result(property = "id", column = "id")})
  List<CardModel> readAllCard(@Param("userId") int userId);

  @Select("SELECT userId FROM CARD WHERE id = #{id}")
  int getUserId(@Param("id")int id);

  @Select("SELECT * FROM CARD WHERE state = 'C' AND id=#{id}")
  CardModel readCard(@Param("id")int id);

  // 식물 카드 개수 제한을 위한 user_id count (user_id가 6이상이면 CreateCard 가 제한됨)
  @Select("SELECT COUNT(*) from CARD WHERE userId = #{userId} AND state='C'")
  int countCard(@Param("userId")int userId);

  @Update("UPDATE CARD SET state = 'D' WHERE id=#{id}")
  void deleteCard(@Param("id")int id);

  @Update("UPDATE CARD SET name=#{name}, nickName=#{nickName}, waterPeriod=#{waterPeriod}, remainPeriod=#{remainPeriod}, updatedAt = now() WHERE id=#{id} AND state ='C'")
  void updateCard(@Param("id")int id, @Param("name") String name, @Param("nickName") String nickName, @Param("waterPeriod") int waterPeriod, @Param("remainPeriod") int remainPeriod);

  @Delete("DELETE FROM CARD WHERE userId = #{userId}")
  void dropCard(@Param("userId")int userId);
}
