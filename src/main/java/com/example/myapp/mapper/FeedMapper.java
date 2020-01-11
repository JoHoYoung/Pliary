package com.example.myapp.mapper;

import com.example.myapp.model.FeedModel;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface FeedMapper {

  @Insert("INSERT INTO FEED(cardId, overDegree, feedAt, createdAt, updatedAt, state)" +
    "VALUES(#{cardId},#{overDegree}, #{feedAt}, now(),now(), 'C')")
  void createFeed(@Param("cardId")int cardId, @Param("overDegree") int overDegree, @Param("feedAt")Date feedAt);

  @Select("SELECT * FROM FEED WHERE cardId=#{cardId} AND state='C'")
  List<FeedModel> readAllFeed(@Param("cardId")int cardId);

  @Select("SELECT * FROM FEED WHERE id=#{id}")
  FeedModel readFeed(@Param("id")int id);

  @Select("SELECT cardId FROM FEED WHERE id=#{id}")
  int getCardId(@Param("id")int id);

  @Update("UPDATE FEED SET updatedAt = now(), overDegree = #{overDegree}, feedAt = #{feedAt} WHERE id = #{feedId} AND state = 'C'")
  void updateFeed(@Param("feedId")int feedId, @Param("overDegree")int overDegree, @Param("feedAt")Date feedAt);

  @Delete("UPDATE FEED SET state = 'D' WHERE id=#{feedId} AND state ='C'")
  void deleteFeed(@Param("feedId")int feedId);

  @Delete("UPDATE FEED SET state ='D' WHERE cardId=#{cardId}")
  void deleteFeedFromCardId(@Param("cardId")int cardId);

  @Select("SELECT userId FROM CARD WHERE CARD.id = (SELECT cardId FROM FEED WHERE id = #{id})")
  int getUserId(@Param("id")int id);

  @Delete("DELETE FROM FEED WHERE id = #{id}")
  void dropFeed(@Param("id")int id);
}
