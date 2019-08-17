package com.example.myapp.mapper;
import com.example.myapp.model.CardModel;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardMapper {

    // 식물 등록
    @Insert("INSERT INTO CARD(uid, user_id, name, nickName, init_period, now_period, state, created_date, updated_date) " +
            "VALUES(#{uid}, #{user_id}, #{name}, #{nickName}, #{init_period}, #{init_period}, 'C', now(), now())")
    int createCard(@Param("uid")String uid,@Param("user_id")String user_id, @Param("name")String name, @Param("nickName")String nickName, @Param("init_period")int init_period);

    // 특정 사용자 식물 전체 목록 select
    @Select("SELECT * FROM CARD WHERE user_id = #{user_id} AND state = 'C'")
    public List<CardModel> readAllCard(@Param("user_id") String user_id);

    @Select("SELECT * FROM CARD WHERE state = 'C' AND uid=#{uid}")
    CardModel readCard(@Param("uid")String uid);

    // 식물 카드 개수 제한을 위한 user_id count (user_id가 6이상이면 CreateCard 가 제한됨)
    @Select("SELECT COUNT(*) from CARD WHERE user_id = #{user_id} AND state='C'")
    int countCard(@Param("user_id")String user_id);

    @Update("UPDATE CARD SET state = 'D' WHERE uid=#{uid}")
    void deleteCard(@Param("uid")String uid);

    @Update("UPDATE CARD SET name=#{name}, nickName=#{nickName}, init_period=#{init_period}, now_period=#{now_period}, updated_date = now() WHERE uid=#{uid} AND state ='C'")
    void updateCard(@Param("uid")String uid, @Param("name")String name,@Param("nickName")String nickName,@Param("init_period")int init_period,@Param("now_period")int now_period);

    // 특정 사용자의 카드 정보 All select
    @Select("SELECT * FROM CARD WHERE user_id = #{user_id} AND state='C'")
    List<CardModel> getCards(int user_id);

}
