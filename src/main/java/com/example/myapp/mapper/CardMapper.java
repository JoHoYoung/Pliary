package com.example.myapp.mapper;
import com.example.myapp.model.CardModel;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardMapper {

    // 식물 카드 만들기 (5개로 제한)
    @Insert("INSERT INTO CARD(uid, user_id, name, nickName, init_period, now_period) " +
            "VALUES(#{user_id}, #{name}, #{nickName}, #{init_period}, #{init_period})")
    public int createCard(String uid,String user_id, String name, String nickName, int init_period);

    @Select("SELECT * FROM CARD WHERE user_id = #{} AND state = 'C'")
    CardModel readCard(String user_id);

    @Select("SELECT * FROM CARD WHERE user_id = #{} AND state = 'C' AND uid=#{card_id}")
    List<CardModel> readAllCard(String user_id, String card_id);
    // 식물 카드 개수 제한을 위한 user_id count (user_id가 6이상이면 create 가 제한됨)
    @Select("SELECT COUNT(*) from CARD WHERE user_id = #{user_id}")
    int cardCount(String user_id);

    // 특정 사용자의 카드 정보 All select
    @Select("SELECT * FROM CARD WHERE user_id = #{user_id}")
    public List<CardModel> getCards(int user_id);

    // 카드 정보 수정
    @Update("")
    public int updateCard(String uid, String name, String nickName, int init_period);

    // 카드 삭제
    @Delete("")
    public int deleteCard();
}
