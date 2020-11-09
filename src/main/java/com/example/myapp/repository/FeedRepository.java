package com.example.myapp.repository;


import com.example.myapp.model.Diary;
import com.example.myapp.model.Feed;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedRepository {

    void insertFeed(@Param("feed") Feed feed);
    List<Diary> selectFeedByDiaryId(@Param("feedId")Integer diaryId);
    Feed selectDiaryById(@Param("id")Integer id);
    void updateFeed(@Param("feed")Feed feed);
    void deleteFeed(@Param("id")Integer id);
}
