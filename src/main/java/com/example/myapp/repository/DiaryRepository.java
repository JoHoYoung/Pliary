package com.example.myapp.repository;

import com.example.myapp.model.Diary;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Long> {

    List<Diary> findByCardId(@Param("cardId") long cardId);

    Page<Diary> findByCardId(@Param("cardId") long cardId, Pageable pageable);

    void deleteByCardId(@Param("cardId") long cardId);
}
