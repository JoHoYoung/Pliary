package com.example.myapp.mapper;

import com.example.myapp.model.DiaryModel;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiaryMapper{
    @Insert("INSERT INTO DIARY(uid, title, card_id, state, body, created_at updated_at) VALUES(#{uid},#{title},#{card_id},'C',#{body},now(),now())")
    void createDiary(@Param("uid")String uid, @Param("title")String title, @Param("card_id")String card_id, @Param("body")String body);

    @Update("UPDATE DIARY SET title =#{title}, body=#{body}, updated_date = now() WHERE uid=#{uid} AND state='C'")
    void updateDiary(@Param("title")String title, @Param("body")String body, @Param("uid")String uid);

    @Select("SELECT * FROM DIARY WHERE uid = #{uid}")
    DiaryModel readDiary(@Param("uid")String uid);

    @Select("SELECT * FROM DIARY WHERE card_id = #{card_id} ORDER BY create_at DESC")
    List<DiaryModel> readAllDiary(@Param("card_id")String card_id);

    @Update("UPDATE DIARY set state='C' WHERE uid = #{uid}")
    List<DiaryModel> deleteDiary(@Param("uid")String uid);
}
