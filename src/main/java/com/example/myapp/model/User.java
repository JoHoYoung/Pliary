package com.example.myapp.model;

import com.example.myapp.model.enumeration.AuthType;
import com.example.myapp.model.enumeration.DataState;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "USER")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String email;

    @Column
    @Enumerated(EnumType.STRING)
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