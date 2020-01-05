package com.example.myapp.context.request.feed;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateFeed {

    @JsonProperty("id")
    private int id;

    @JsonProperty("cardId")
    private int cardId;

    @JsonProperty("overDegree")
    private int overDegree;
}
