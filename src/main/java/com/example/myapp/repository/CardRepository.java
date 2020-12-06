package com.example.myapp.repository;

import com.example.myapp.model.Card;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {

    List<Card> findByUserId(@Param("userId") long userId);

    int countByUserId(@Param("userId") long userId);

    Page<Card> findByUserId(@Param("userId") long userId, Pageable pageable);

}
