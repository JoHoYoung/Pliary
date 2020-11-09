package com.example.myapp.model;

import com.example.myapp.model.enumeration.AttatchmentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Attatchment {
    private Integer id;
    private Integer targetId;
    private Integer userId;
    private AttatchmentType attatchmentType;
    private String url;
    private String state;
    private String filename;
    private Date createdAt;
    private Date updatedAt;
}
