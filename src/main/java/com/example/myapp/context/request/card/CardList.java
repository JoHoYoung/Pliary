package com.example.myapp.context.request.card;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CardList {

    @JsonProperty("firstResult")
    Integer firstResult;
    @JsonProperty("maxResults")
    Integer maxResults;
}
