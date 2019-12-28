package com.example.myapp.context.request.diary;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateDiary {
    @JsonProperty("id")
    private String id;

    @JsonProperty("cardId")
    private String cardId;

    @JsonProperty("title")
    private String title;

    @JsonProperty("body")
    private String body;

    @JsonProperty("state")
    private char state;
}