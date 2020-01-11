package com.example.myapp.context.request.diary;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateDiary {
  @JsonProperty("id")
  private int id;

  @JsonProperty("cardId")
  private int cardId;

  @JsonProperty("title")
  private String title;

  @JsonProperty("body")
  private String body;

  @JsonProperty("state")
  private char state;
}