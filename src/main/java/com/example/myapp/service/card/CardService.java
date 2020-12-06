package com.example.myapp.service.card;

import com.example.myapp.context.request.card.CreateCard;
import com.example.myapp.context.request.card.UpdateCard;
import com.example.myapp.dto.CardDTO;
import com.example.myapp.model.Card;
import com.example.myapp.model.User;
import com.example.myapp.repository.CardRepository;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Transactional
@Service
public class CardService {

    @Autowired
    CardRepository cardRepository;

    public CardDTO save(User user, @Valid CreateCard param) {
        Date current = new Date();
        Card card = Card.builder()
            .createdAt(current).engName(param.getEngName()).krName(param.getKrName())
            .name(param.getName()).nickName(param.getNickname()).waterPeriod(param.getWaterPeriod())
            .plantType(param.getPlantType()).user(user).build();
        this.cardRepository.save(card);
        return card.toDto();
    }

    public CardDTO update(User user, UpdateCard param) {
        Card card = this.cardRepository.getOne(param.getId());
        boolean idUpdate = false;
        if (param.getName() != null) {
            card.setName(param.getName());
            idUpdate = true;
        }
        if (param.getEngName() != null) {
            card.setEngName(param.getEngName());
            idUpdate = true;
        }
        if (param.getKrName() != null) {
            card.setKrName(param.getKrName());
            idUpdate = true;
        }
        if (param.getNickName() != null) {
            card.setNickName(param.getNickName());
            idUpdate = true;
        }
        if (param.getPlantType() != null) {
            card.setPlantType(param.getPlantType());
            idUpdate = true;
        }
        if (param.getWaterPeriod() > 0) {
            card.setWaterPeriod(param.getWaterPeriod());
            idUpdate = true;
        }
        if (idUpdate) {
            Date current = new Date();
            card.setUpdatedAt(current);
        }
        return card.toDto();
    }

    public Card getById(long cardId) {
        return this.cardRepository.getOne(cardId);
    }

    public List<CardDTO> getByUserId(long userId) {
        List<Card> list = this.cardRepository.findByUserId(userId);
        List<CardDTO> dtos = list.stream().map(card -> card.toDto()).collect(Collectors.toList());
        return dtos;
    }

    public List<CardDTO> getByUserId(long userId, Integer firstResult, Integer maxResults) {
        List<Card> list = new ArrayList<>();
        if (firstResult == null && maxResults == null) {
            list = this.cardRepository.findByUserId(userId);
        } else {
            list = this.cardRepository.findByUserId(userId, PageRequest.of(firstResult, maxResults)).getContent();
        }
        List<CardDTO> dtos = list.stream().map(card -> card.toDto()).collect(Collectors.toList());
        return dtos;
    }

    public void delete(long cardId) {
        Card card = this.cardRepository.getOne(cardId);
        card.setDeleted(true);
    }

    public void dropCard(long userId) {
        List<Card> cardList = this.cardRepository.findByUserId(userId);
        cardList.forEach(card -> card.setDeleted(true));
    }

    public int countCardByUserId(long userId) {
        return this.cardRepository.countByUserId(userId);
    }


}
