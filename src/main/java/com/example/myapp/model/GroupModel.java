package com.example.myapp.model;


import lombok.Data;
import org.apache.ibatis.annotations.Many;

import java.util.Date;
import java.util.List;

@Data
public class GroupModel {

    private int uid;
    private String email;
    private String password;
    private char state;
    private String name;
    private String nickname;
    private String birth;
    private String phone;
    private String token;
    private int provider;
    private Date create_at;
    private Date updated_at;
    List<TestModel> list;
}
