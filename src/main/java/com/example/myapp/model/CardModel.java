package com.example.myapp.model;

import lombok.Data;

import java.util.Date;

@Data
public class CardModel {
    private String uid; // 고유 id
    private String user_id;
    private String name; // 식물 이름
    private String nickname; // 사용자 지정 애칭
    private String start_date;
    int init_period;
    int remain_period; // count 필요?
    private Date created_at;
    private Date updated_at;
}
