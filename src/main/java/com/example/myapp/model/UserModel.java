package com.example.myapp.model;

import lombok.*;
import org.apache.ibatis.type.Alias;

import java.util.Date;

@Data
@Alias("USER")
public class UserModel {
    private String uid;
    private String email;
    private String state;
    private String name;
    private String nickname;
    private String birth;
    private String phone;
    private String token;
    private int provider;
    private Date created_at;
    private Date updated_at;
}