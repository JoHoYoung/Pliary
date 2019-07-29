package com.example.myapp.context;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateCard {

    @JsonProperty("uid")
    private String uid;

    @JsonProperty("name")
    private String name;

    @JsonProperty("nickname")
    private String nickname;

    @JsonProperty("init_period")
    private int init_period;

}
