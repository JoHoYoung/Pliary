package com.example.myapp.model;

import lombok.Data;

import java.util.Date;

@Data
public class CardModel {
    private String uid;
    private String user_id;
    private String name;
    private String nickname;
    private String start_date;
    int init_period;
<<<<<<< HEAD
    int now_period; // count 필요?
=======
    int remain_period;
>>>>>>> fe4e6b82ea21bc12febb0ad8c8ac31de41283752
    private Date created_at;
    private Date updated_at;
}
