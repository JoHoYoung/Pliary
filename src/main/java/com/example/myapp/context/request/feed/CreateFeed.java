package com.example.myapp.context.request.feed;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateFeed {

  @JsonProperty("cardId")
  private int cardId;
  @JsonProperty("overDegree")
  private int overDegree;
  @JsonProperty("feedAt")
  private Date feedAt;

}
