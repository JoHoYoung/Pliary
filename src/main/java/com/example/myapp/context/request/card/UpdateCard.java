package com.example.myapp.context.request.card;

import com.example.myapp.model.enumeration.PlantType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCard {

    @JsonProperty("id")
    private long id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("nickName")
    private String nickName;
    @JsonProperty("waterPeriod")
    private int waterPeriod;
    @JsonProperty("remainPeriod")
    private int remainPeriod;
    @JsonProperty("krName")
    private String krName;
    @JsonProperty("engName")
    private String engName;
    @JsonProperty("plantType")
    private PlantType plantType;

}
