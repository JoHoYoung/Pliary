package com.example.myapp.context.feed;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateFeed {

    @JsonProperty("uid")
    private String uid;

    @JsonProperty("card_id")
    private String card_id;

    @JsonProperty("over_degree")
    private int over_degree;
}
