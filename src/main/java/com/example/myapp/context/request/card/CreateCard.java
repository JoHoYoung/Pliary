package com.example.myapp.context.request.card;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateCard {

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

//{
//	"name":"Plant",
//	"nickname":"CAT",
//	"waterPeriod":10,
//  "remainPeriod":10,
//	"krName":"다욱이",
//	"engName":"Daaaa",
//	"typeId":"23ewwq-123eweqwqe",
//}