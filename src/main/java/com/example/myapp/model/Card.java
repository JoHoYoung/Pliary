package com.example.myapp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Card {

    private Integer id;
    private Integer userId;
    private Integer typeId;

    private String name;
    private String nickName;
    private String engName;
    private String krName;
    private String state;

    private Integer waterPeriod;

    private Date createdAt;
    private Date updatedAt;

}
