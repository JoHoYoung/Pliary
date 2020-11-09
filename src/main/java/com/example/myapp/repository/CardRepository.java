package com.example.myapp.repository;

import com.example.myapp.model.Card;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository {

    void insertCard(@Param("card")Card card);
    List<Card> selectCardsByUserId(@Param("userId")Integer userId);
    Integer selectUserIdById(@Param("id")Integer id);
    Card selectCardById(@Param("id")Integer id);
    Integer countCardByUserId(@Param("userId")Integer userId);
    void deleteCardById(@Param("id")Integer id);
    void updateCard(@Param("card")Card card);
}
