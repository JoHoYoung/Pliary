package com.example.myapp.controller;

import com.example.myapp.ErrorCode;
import com.example.myapp.bo.user.UserBO;
import com.example.myapp.context.request.card.CardList;
import com.example.myapp.context.request.card.CreateCard;
import com.example.myapp.context.request.card.UpdateCard;
import com.example.myapp.context.user.Session;
import com.example.myapp.dto.CardDTO;
import com.example.myapp.exception.PliaryException;
import com.example.myapp.model.Card;
import com.example.myapp.model.User;
import com.example.myapp.response.BaseResponse;
import com.example.myapp.response.DataListResponse;
import com.example.myapp.response.DataResponse;
import com.example.myapp.response.ErrorResponse;
import com.example.myapp.service.card.CardService;
import com.example.myapp.util.PliaryPolicy;
import com.example.myapp.util.Util;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import javax.validation.Valid;
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
@RequestMapping(value = "/card")
public class CardController {

    @Autowired
    CardService cardService;
    @Autowired
    UserBO userBO;
    @Autowired
    ObjectMapper objectMapper;

    @RequestMapping(value = "/create", method = RequestMethod.PUT)
    public ResponseEntity<BaseResponse> createCard(@RequestAttribute("session") Session session, @Valid @RequestBody CreateCard param) {

        if (this.cardService.countCardByUserId(session.getId()) > PliaryPolicy.MAX_CARD_COUNT) {
            throw new PliaryException(ErrorCode.EXCEED_MAX_CARD_NUMBER);
        }
        User user = this.userBO.getUser(session.getId());
        CardDTO cardDTO = this.cardService.save(user, param);
        final BaseResponse response = new DataResponse<>(cardDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<BaseResponse> updateCard(@RequestAttribute("session") Session session, @Valid @RequestBody UpdateCard param) {

        Card card = this.cardService.getById(param.getId());
        if (card == null) {
            final BaseResponse response = new ErrorResponse(ErrorCode.DATA_NOT_FOUND);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        User user = this.userBO.getUser(session.getId());
        Util.numberDataAthorization(user.getId(), session.getId());

        CardDTO cardDTO = this.cardService.update(user, param);
        final BaseResponse response = new DataResponse<>(cardDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseEntity<BaseResponse> readAllCard(@RequestAttribute("session") Session session, @RequestBody CardList request) {

        List<CardDTO> cards = this.cardService.getByUserId(session.getId());
        final BaseResponse response = new DataListResponse<>(cards);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public ResponseEntity<BaseResponse> getList(@RequestAttribute("session") Session session, @RequestBody CardList request) {

        List<CardDTO> cards = this.cardService.getByUserId(session.getId(), request.getFirstResult(), request.getMaxResults());
        final BaseResponse response = new DataListResponse<>(cards);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResponseEntity<BaseResponse> readCard(@RequestAttribute("session") Session session, @RequestParam("id") int id) {
        Card card = this.cardService.getById(id);

        if (card == null) {
            final BaseResponse response = new ErrorResponse(ErrorCode.DATA_NOT_FOUND);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        User user = this.userBO.getUser(session.getId());
        Util.numberDataAthorization(user.getId(), session.getId());

        final BaseResponse response = new DataResponse<>(card.toDto());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public ResponseEntity<BaseResponse> deleteCard(@RequestAttribute("session") Session session, @RequestParam("id") int id) {

        Card card = this.cardService.getById(id);

        if (card == null) {
            final BaseResponse response = new ErrorResponse(ErrorCode.DATA_NOT_FOUND);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        User user = this.userBO.getUser(session.getId());
        Util.numberDataAthorization(user.getId(), session.getId());

        this.cardService.delete(id);
        final BaseResponse response = new BaseResponse();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
