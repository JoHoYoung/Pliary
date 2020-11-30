package com.example.myapp.model;

import com.example.myapp.model.enumeration.AuthType;
import com.example.myapp.model.enumeration.DataState;
import lombok.*;
import org.apache.ibatis.type.Alias;

import java.util.Date;

@Data
@Alias("USER")
public class User {

    private int id;
    private String email;
    private DataState state;
    private String name;
    private String nickname;
    private String birth;
    private String phone;
    private String oauthToken;
    private String refreshToken;
    private AuthType authType;
    private Date createdAt;
    private Date updatedAt;

}