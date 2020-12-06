package com.example.myapp.controller;

import com.example.myapp.ErrorCode;
import com.example.myapp.context.request.diary.CreateDiary;
import com.example.myapp.context.request.diary.DiaryList;
import com.example.myapp.context.request.diary.UpdateDiary;
import com.example.myapp.context.user.Session;
import com.example.myapp.dto.DiaryDTO;
import com.example.myapp.model.Card;
import com.example.myapp.model.Diary;
import com.example.myapp.model.User;
import com.example.myapp.response.BaseResponse;
import com.example.myapp.response.DataListResponse;
import com.example.myapp.response.DataResponse;
import com.example.myapp.response.ErrorResponse;
import com.example.myapp.service.card.CardService;
import com.example.myapp.service.diary.DiaryService;
import com.example.myapp.util.Util;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/diary")
public class DiaryController {

    @Autowired
    DiaryService diaryService;

    @Autowired
    CardService cardService;

    @RequestMapping(value = "/create", method = RequestMethod.PUT)
    public ResponseEntity<BaseResponse> createDiary(@RequestAttribute("session") Session session, @RequestBody CreateDiary param) {

        Card card = this.cardService.getById(param.getCardId());
        User user = card.getUser();
        Util.numberDataAthorization(user.getId(), session.getId());

        DiaryDTO diaryDTO = this.diaryService.save(user, card, param);

        final BaseResponse response = new DataResponse<>(diaryDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity updateDiary(@RequestAttribute("session") Session session, @RequestBody UpdateDiary param) {

        Diary diary = this.diaryService.getById(param.getId());

        if (diary == null) {
            final BaseResponse response = new ErrorResponse(ErrorCode.DATA_NOT_FOUND);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        User user = diary.getUser();
        Util.numberDataAthorization(user.getId(), session.getId());

        DiaryDTO diaryDTO = this.diaryService.update(param);

        final BaseResponse response = new DataResponse<>(diaryDTO);
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResponseEntity readDiary(@RequestAttribute("session") Session session, @RequestParam("diaryId") int diaryId) {
        Diary diary = this.diaryService.getById(diaryId);
        if (diary == null) {
            final BaseResponse response = new ErrorResponse(ErrorCode.DATA_NOT_FOUND);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        User user = diary.getUser();
        Util.numberDataAthorization(user.getId(), session.getId());

        final BaseResponse response = new DataResponse<>(diary.toDto());
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public ResponseEntity readAllDiary(@RequestAttribute("session") Session session, @RequestBody DiaryList param) {

        Card card = this.cardService.getById(param.getCardId());
        if (card == null) {
            final BaseResponse response = new ErrorResponse(ErrorCode.DATA_NOT_FOUND);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        User user = card.getUser();
        Util.numberDataAthorization(user.getId(), session.getId());

        List<DiaryDTO> diaries = this.diaryService.getByCardId(param.getCardId(), param.getFirstResult(), param.getMaxResults());

        final BaseResponse response = new DataListResponse<>(diaries);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ResponseEntity deleteDiary(@RequestAttribute("session") Session session, @RequestParam("diaryId") int diaryId) {

        Diary diary = this.diaryService.getById(diaryId);
        if (diary == null) {
            final BaseResponse response = new ErrorResponse(ErrorCode.DATA_NOT_FOUND);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        User user = diary.getUser();
        Util.numberDataAthorization(user.getId(), session.getId());

        this.diaryService.delete(diaryId);
        final BaseResponse response = new BaseResponse();
        return new ResponseEntity(response, HttpStatus.OK);
    }

}
