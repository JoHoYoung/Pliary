package com.example.myapp.mapper;

import com.example.myapp.model.CardModel;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiaryMapper {

    // 다이어리 등록
    @Insert("")
     int createDiary();

    // 특정 카드(식물) 다이어리 읽기
    @Select("")
    List<CardModel> readAllDiary();

    // 다이어리 삭제 (1개 지정)
    @Delete("")
    int deleteDiary();

    // 다이어리 수정
    @Update("")
    int updateDiary();

}
