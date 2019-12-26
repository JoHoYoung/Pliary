package com.example.myapp.context.request.feed;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateFeed {

    @JsonProperty("id")
    private String id;

    @JsonProperty("cardId")
    private String cardId;

    @JsonProperty("overDegree")
    private int overDegree;
}
