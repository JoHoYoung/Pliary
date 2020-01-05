package com.example.myapp.context.request.card;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCard {

  @JsonProperty("id")
  private int id;
  @JsonProperty("name")
  private String name;
  @JsonProperty("nickname")
  private String nickname;
  @JsonProperty("waterPeriod")
  private int waterPeriod;
  @JsonProperty("remainPeriod")
  private int remainPeriod;
  @JsonProperty("krName")
  private String krName;
  @JsonProperty("engName")
  private String engName;
  @JsonProperty("typeId")
  private int typeId;

}
