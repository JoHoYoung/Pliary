package com.example.myapp.context;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateCard {

    @JsonProperty("uid")
    private int uid;

    @JsonProperty("user_id")
    private int user_id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("nickname")
    private String nickname;

    @JsonProperty("start_date")
    private String start_date;

    @JsonProperty("cycle")
    private int cycle;

    public CreateCard(int uid, int user_id, String name, String nickname, String start_date, int cycle) {
        this.uid = uid;
        this.user_id = user_id;
        this.name = name;
        this.nickname = nickname;
        this.start_date = start_date;
        this.cycle = cycle;
    }
}
