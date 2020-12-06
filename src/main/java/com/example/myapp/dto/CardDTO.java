package com.example.myapp.dto;

import com.example.myapp.model.enumeration.PlantType;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardDTO {

    private long id;
    private PlantType plantType;
    private String name;
    private String nickName;
    private String engName;
    private String krName;
    private Integer waterPeriod;
    private Date createdAt;
    private Date updatedAt;
    private boolean isDeleted;
}
