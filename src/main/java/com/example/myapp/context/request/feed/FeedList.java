package com.example.myapp.context.request.feed;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FeedList {

    @JsonProperty("cardId")
    long cardId;
    @JsonProperty("startTime")
    Integer startTime;
    @JsonProperty("endTime")
    Integer endTime;
}
