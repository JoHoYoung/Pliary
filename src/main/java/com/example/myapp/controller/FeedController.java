package com.example.myapp.controller;

import com.example.myapp.ErrorCode;
import com.example.myapp.context.request.feed.CreateFeed;
import com.example.myapp.context.request.feed.FeedList;
import com.example.myapp.context.user.Session;
import com.example.myapp.model.Card;
import com.example.myapp.model.Feed;
import com.example.myapp.model.User;
import com.example.myapp.response.BaseResponse;
import com.example.myapp.response.DataListResponse;
import com.example.myapp.response.ErrorResponse;
import com.example.myapp.service.card.CardService;
import com.example.myapp.service.feed.FeedService;
import com.example.myapp.util.Util;
import com.fasterxml.jackson.databind.ObjectMapper;
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
@RequestMapping(value = "/feed")
public class FeedController {

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    FeedService feedService;
    @Autowired
    CardService cardService;

    @RequestMapping(value = "", method = RequestMethod.PUT)
    public ResponseEntity feed(@RequestAttribute("session") Session session,
        @RequestBody CreateFeed param) {

        Card card = this.cardService.getById(param.getCardId());

        if (card == null) {
            final BaseResponse response = new ErrorResponse(ErrorCode.DATA_NOT_FOUND);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        User user = card.getUser();
        Util.numberDataAthorization(user.getId(), session.getId());

        this.feedService.feed(param.getCardId());

        final BaseResponse response = new BaseResponse();
        return new ResponseEntity(response, HttpStatus.OK);

    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public ResponseEntity readAllFeed(@RequestAttribute("session") Session session,
        @RequestBody FeedList request) {
        Card card = this.cardService.getById(request.getCardId());

        if (card == null) {
            final BaseResponse response = new ErrorResponse(ErrorCode.DATA_NOT_FOUND);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        User user = card.getUser();
        Util.numberDataAthorization(user.getId(), session.getId());
        List<Feed> Feeds = this.feedService.getList(request);

        final BaseResponse response = new DataListResponse<>(Feeds);
        return new ResponseEntity(response, HttpStatus.OK);

    }


    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ResponseEntity deleteFeed(@RequestAttribute("session") Session session,
        @RequestParam("feedId") long feedId) {

        Feed feed = this.feedService.getById(feedId);
        if (feed == null) {
            final BaseResponse response = new ErrorResponse(ErrorCode.DATA_NOT_FOUND);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        User user = feed.getUser();
        Util.numberDataAthorization(user.getId(), session.getId());

        this.feedService.delete(feedId);

        final BaseResponse response = new BaseResponse();
        return new ResponseEntity(response, HttpStatus.OK);

    }

}
