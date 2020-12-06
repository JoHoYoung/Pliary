package com.example.myapp.model;

import com.example.myapp.dto.DiaryDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "DIARY")
public class Diary implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "idUser", nullable = false, insertable = true, updatable = false, foreignKey = @javax.persistence.ForeignKey(name = "none"))
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "idCard", nullable = false, insertable = true, updatable = false, foreignKey = @javax.persistence.ForeignKey(name = "none"))
    private Card card;

    private String title;
    private String body;
    private Date createdAt;
    private Date updatedAt;
    private boolean isDeleted;

    public DiaryDTO toDto() {
        return DiaryDTO.builder()
            .id(this.id)
            .title(this.title)
            .body(this.body)
            .createdAt(this.createdAt)
            .updatedAt(this.updatedAt)
            .isDeleted(this.isDeleted)
            .build();
    }
}
