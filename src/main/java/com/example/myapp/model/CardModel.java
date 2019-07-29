package com.example.myapp.model;

import lombok.Data;

@Data
public class CardModel {
    String uid; // 고유 id
    String user_id;
    String name; // 식물 이름
    String nickname; // 사용자 지정 애칭
    String start_date;
    int init_period;
    int remain_period; // count 필요?
}
