package com.example.myapp.controller.test;


import com.example.myapp.controller.test.TestModel;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class GroupModel {

    private String id;
    private String email;
    private String password;
    private char state;
    private String name;
    private String nickname;
    private String birth;
    private String phone;
    private String token;
    private int provider;
    private Date createAt;
    private Date updatedAt;
    List<TestModel> list;
}
