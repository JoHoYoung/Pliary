package com.example.myapp.mapper;
import com.example.myapp.model.CardModel;
import org.apache.ibatis.annotations.*;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardMapper {

    // 식물 카드 만들기 (5개로 제한)
    @Insert("INSERT INTO CARD(uid, user_id, name, nickName, init_period, now_period, state) " +
            "VALUES(#{uid}, #{user_id}, #{name}, #{nickName}, #{init_period}, #{init_period}, 'C')")
    public int createCard(@Param("uid")String uid,@Param("user_id")String user_id, @Param("name")String name, @Param("nickName")String nickName, @Param("init_period")int init_period);

    @Select("SELECT * FROM CARD WHERE user_id = #{user_id} AND state = 'C'")
    List<CardModel> readAllCard(@Param("user_id")String user_id);

    @Select("SELECT * FROM CARD WHERE state = 'C' AND uid=#{uid}")
    CardModel readCard(@Param("uid")String uid);
    // 식물 카드 개수 제한을 위한 user_id count (user_id가 6이상이면 CreateCard 가 제한됨)
    @Select("SELECT COUNT(*) from CARD WHERE user_id = #{user_id}")
    int countCard(@Param("user_id")String user_id);

    @Delete("DELETE FROM CARD WHERE uid=#{uid}")
    void deleteCard(@Param("uid")String uid);

    @Update("UPDATE CARD SET name=#{name}, nickName=#{nickName}, init_period=#{init_period}, now_period=#{now_period} WHERE uid=#{uid}")
    void updateCard(@Param("uid")String uid, @Param("name")String name,@Param("nickName")String nickName,@Param("init_period")int init_period,@Param("now_period")int now_period);

    // 특정 사용자의 카드 정보 All select
    @Select("SELECT * FROM CARD WHERE user_id = #{user_id}")
    public List<CardModel> getCards(int user_id);

    // 카드 정보 수정
    //@Update("")
    //public int updateCard(String uid, String name, String nickName, int init_period);

}