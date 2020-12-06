package com.example.myapp.context.request.diary;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DiaryList {

    @JsonProperty("cardId")
    long cardId;
    @JsonProperty("firstResult")
    Integer firstResult;
    @JsonProperty("maxResults")
    Integer maxResults;
}
