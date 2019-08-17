package com.example.myapp.mapper;

import com.example.myapp.model.DiaryModel;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface DiaryMapper {

    // 다이어리 등록
    @Insert("INSERT INTO DIARY(uid, card_id, title, body, created_at, updated_at, state) " +
            "VALUES(#{uid}, #{card_id}, #{title}, #{body}, now(), now(), 'C')")
    int createDiary(String uid, String card_id, String title, String body);

    // 특정 카드(식물) 다이어리 읽기
    @Select("SELECT * FROM DIARY WHERE card_id=#{card_id}")
    ArrayList<DiaryModel> readAllDiary(String card_id);

    // 하나의 다이어리(선택) 읽기
    @Select("SELECT * FROM DIARY WHERE uid = #{uid}")
    DiaryModel readDiary(String uid);

    // 다이어리 삭제 (1개 지정) - state = 'D' 로 update
    @Update("UPDATE DIARY SET state='D', updated_at=now() WHERE uid=#{uid}")
    int deleteDiary(String uid);

    // 특정 카드에 대한 다이어리 전부 삭제
    @Update("UPDATE DIARY SET state='D', updated_at=now() WHERE card_id=#{card_id}")
    int deleteAllDiary(String card_id);

    // 다이어리 수정
    @Update("UPDATE DIARY SET title=#{title}, body=#{body}, updated_at=#{updated_at} " +
            "WHERE uid = #{uid}")
    int updateDiary(String uid, String title, String body);

}