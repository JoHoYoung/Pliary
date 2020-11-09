package com.example.myapp.repository;

import com.example.myapp.model.Card;
import com.example.myapp.model.Diary;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiaryRepository {
    void insertDiary(@Param("diary")Diary diary);
    List<Card> selectDiaryByCardId(@Param("cardId")Integer cardId);
    Diary selectDiaryById(@Param("id")Integer Id);
    void deleteDiary(@Param("id")Integer Id);
    void updateDidary(@Param("diary")Diary diary);
    Integer selectUserIdById(@Param("id")Integer id);
    void deleteDiaryByCardId(@Param("cardId")Integer cardId);
}
