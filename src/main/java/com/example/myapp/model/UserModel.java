package com.example.myapp.model;

import lombok.*;
import org.apache.ibatis.type.Alias;

import java.util.Date;

@Data
@Alias("USER")
public class UserModel {
    private int uid;
    private String email;
    private String password;
    private String temp_password;
    private char state;
    private String name;
    private String nickname;
    private String birth;
    private String phone;
    private String token;
    private int provider;
    private Date create_at;
    private Date updated_at;

}