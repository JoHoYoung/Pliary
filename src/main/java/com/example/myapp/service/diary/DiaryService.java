package com.example.myapp.service.diary;

import com.example.myapp.context.request.diary.CreateDiary;
import com.example.myapp.context.request.diary.UpdateDiary;
import com.example.myapp.dto.DiaryDTO;
import com.example.myapp.model.Card;
import com.example.myapp.model.Diary;
import com.example.myapp.model.User;
import com.example.myapp.repository.DiaryRepository;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Transactional
@Service
public class DiaryService {

    @Autowired
    DiaryRepository diaryRepository;

    public DiaryDTO save(User user, Card card, CreateDiary param) {
        Date current = new Date();
        Diary diary = Diary.builder()
            .user(user)
            .card(card)
            .title(param.getTitle())
            .body(param.getBody())
            .createdAt(current)
            .build();
        this.diaryRepository.save(diary);
        return diary.toDto();
    }

    public DiaryDTO update(UpdateDiary param) {
        boolean isUpdate = false;
        Diary diary = this.diaryRepository.getOne(param.getId());
        if (param.getBody() != null) {
            diary.setBody(param.getBody());
            isUpdate = true;
        }
        if (param.getTitle() != null) {
            diary.setTitle(param.getTitle());
            isUpdate = true;
        }
        if (isUpdate) {
            Date current = new Date();
            diary.setUpdatedAt(current);
        }
        return diary.toDto();
    }

    public Diary getById(long diaryId) {
        return this.diaryRepository.getOne(diaryId);
    }

    public List<Diary> getByCardId(long cardId) {
        return this.diaryRepository.findByCardId(cardId);
    }

    public List<DiaryDTO> getByCardId(long cardId, Integer firstResult, Integer maxResults) {
        List<Diary> list = new ArrayList<>();
        if (firstResult == null || maxResults == null) {
            list = this.diaryRepository.findByCardId(cardId);
        }
        list = this.diaryRepository.findByCardId(cardId, PageRequest.of(firstResult, maxResults)).getContent();

        List<DiaryDTO> dtos = list.stream().map(diary -> diary.toDto()).collect(Collectors.toList());
        return dtos;
    }

    public void update(long id, String title, String body) {
        Diary diary = this.diaryRepository.getOne(id);
        diary.setTitle(title);
        diary.setBody(body);
    }

    public void delete(long diaryId) {
        Diary diary = this.diaryRepository.getOne(diaryId);
        diary.setDeleted(true);
    }

    public void dropDiary(long cardId) {
        List<Diary> diaryList = this.diaryRepository.findByCardId(cardId);
        diaryList.forEach(diary -> diary.setDeleted(true));
    }


}
