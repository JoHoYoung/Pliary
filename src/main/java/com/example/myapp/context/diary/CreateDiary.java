package com.example.myapp.context.diary;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateDiary {

    @JsonProperty("uid")
    private String uid;

    @JsonProperty("card_id")
    private String card_id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("body")
    private String body;


}
