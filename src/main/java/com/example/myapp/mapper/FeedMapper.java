package com.example.myapp.mapper;

import com.example.myapp.model.FeedModel;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedMapper {

  @Insert("INSERT INTO FEED(cardId, overDegree, createdAt, updatedAt, state)" +
    "VALUES(#{cardId},#{overDegree},now(),now(), 'C')")
  void createFeed(@Param("cardId")int cardId, @Param("overDegree") int overDegree);

  @Select("SELECT * FROM FEED WHERE cardId=#{cardId} AND state='C'")
  List<FeedModel> readAllFeed(@Param("cardId")int cardId);

  @Update("UPDATE FEED SET updatedAt = now(), overDegree = #{overDegree} WHERE id = #{feedId} AND state = 'C'")
  void updateFeed(@Param("feedId")int feedId);

  @Delete("UPDATE FEED SET state = 'D' WHERE id=#{feedId} AND state ='C'")
  void deleteFeed(@Param("feedId")int feedId);

  @Delete("UPDATE FEED SET state ='D' WHERE cardId=#{cardId}")
  void deleteFeedFromCardId(@Param("cardId")int cardId);
}
