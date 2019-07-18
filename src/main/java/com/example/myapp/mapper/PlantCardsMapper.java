package com.example.myapp.mapper;
import com.example.myapp.model.PlantCardsModel;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlantCardsMapper {

    // 식물 카드 만들기 (5개로 제한)
    @Insert("INSERT INTO plant_cards(user_id, name, nickName, start_date, cycle) " +
            "VALUES(#{user_id}, #{name}, #{nickName}, #{start_date}, #{cycle})")
    public int createCard(int user_id, String name, String nickName, String start_date, int cycle);

    // 식물 카드 개수 제한을 위한 user_id count (user_id가 6이상이면 create 가 제한됨)
    @Select("SELECT COUNT(*) from plant_cards WHERE user_id = #{user_id}")
    public int cardCount(int user_id);

    // 특정 사용자의 카드 정보 All select
    @Select("SELECT * FROM plant_cards WHERE user_id = #{user_id}")
    public List<PlantCardsModel> getCards(int user_id);

    // 카드 정보 수정
    @Update("")
    public int updateForm(int uid, String name, String nickName, String start_date, int cycle);

    // 카드 삭제
    @Delete("")
    public int deleteCard();
}
