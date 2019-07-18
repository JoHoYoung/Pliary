package com.example.myapp.model;

import lombok.Data;

@Data
public class PlantCardsModel {
    int uid; // 고유 id
    int user_id;
    String name; // 식물 이름
    String nickname; // 사용자 지정 애칭
    String start_date;
    int cycle;
    int cycle_counting; // count 필요?
}
