package com.example.myapp.repository;


import com.example.myapp.model.Diary;
import com.example.myapp.model.Feed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedRepository extends JpaRepository<Feed, Long> {

    List<Feed> findByCardId(@Param("cardId") long cardId);
}
