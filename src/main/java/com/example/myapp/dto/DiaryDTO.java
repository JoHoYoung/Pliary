package com.example.myapp.dto;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiaryDTO {

    private long id;
    private String title;
    private String body;
    private Date createdAt;
    private Date updatedAt;
    private boolean isDeleted;
}
