package com.example.myapp.mapper;

import com.example.myapp.model.FeedModel;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedMapper {

    @Insert("INSERT INTO FEED(uid, card_id, over_degree, created_at, updated_at)" +
            "VALUES(#{uid},#{card_id},#{over_degree},now(),now())")
    void createFeed(@Param("uid")String uid, @Param("card_id")String card_id, @Param("over_degree")int over_degree);

    @Select("SELECT * FROM FEED WHERE card_id=#{card_id}")
    List<FeedModel> readAllFeed(@Param("card_id")String card_id);

    @Update("UPDATE FEED SET updated_date = now(), over_degree = #{over_degree} WHERE uid = #{feed_id}")
    void updateFeed(@Param("feed_id")String feed_id);

    @Delete("DELETE FROM FEED WHERE uid=#{feed_id}")
    void deleteFeed(@Param("feed_id")String feed_id);

    @Delete("DELETE FROM FEED WHERE card_id=#{card_id}")
    void deleteFeedFromCardId(@Param("card_id")String card_id);
}
