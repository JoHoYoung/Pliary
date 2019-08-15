package com.example.myapp.mapper;
import com.example.myapp.model.CardModel;
import org.apache.ibatis.annotations.*;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardMapper {

    // 식물 등록
    @Insert("INSERT INTO CARD(uid, user_id, name, nickName, init_period, now_period, state) " +
            "VALUES(#{uid}, #{user_id}, #{name}, #{nickName}, #{init_period}, #{init_period}, 'C')")
    public int createCard(@Param("uid") String uid, @Param("user_id") String user_id, @Param("name")String name, @Param("nickName")String nickName, @Param("init_period")int init_period);

    // 특정 사용자 식물 전체 목록 select
    @Select("SELECT * FROM CARD WHERE user_id = #{user_id} AND state = 'C'")
    public List<CardModel> readAllCard(@Param("user_id") String user_id);

    // 사용자의 특정 식물 정보 select
    @Select("SELECT * FROM CARD WHERE user_id = #{user_id} AND state = 'C' AND card_id = #{card_id}")
    public CardModel readCard(@Param("user_id") String user_id, @Param("card_id") String card_id);

    // 식물 카드 개수 제한을 위한 user_id count (user_id가 6이상이면 create 가 제한됨)
    @Select("SELECT COUNT(*) from CARD WHERE user_id = #{user_id}")
    public int countCard(@Param("user_id") String user_id);

    // 특정 식물 카드 삭제
    @Delete("DELETE FROM CARD WHERE uid = #{uid}")
    public int deleteCard(@Param("uid") String uid);

    // 식물 정보 update
    @Update("UPDATE CARD SET(name=#{name}, nickName=#{nickName}, init_period=#{init_period}, now_period=#{now_period} " +
            "WHERE uid = #{uid}")
    public int updateCard(@Param("uid") String uid, @Param("name") String name, @Param("nickName") String nickName, @Param("init_period") int init_period, @Param("now_period") int now_period);
}
