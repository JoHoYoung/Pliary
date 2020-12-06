package com.example.myapp.model;

import com.example.myapp.dto.CardDTO;
import com.example.myapp.model.enumeration.PlantType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "CARD")
public class Card implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "idUser", nullable = false, insertable = true, updatable = false, foreignKey = @javax.persistence.ForeignKey(name = "none"))
    private User user;

    @Column
    @Enumerated(EnumType.STRING)
    private PlantType plantType;

    private String name;
    private String nickName;
    private String engName;
    private String krName;

    private Integer waterPeriod;

    private Date createdAt;
    private Date updatedAt;

    private boolean isDeleted;

    public CardDTO toDto() {
        return CardDTO.builder()
            .id(this.id)
            .name(this.name)
            .engName(this.engName)
            .krName(this.krName)
            .plantType(this.plantType)
            .nickName(this.nickName)
            .createdAt(this.createdAt)
            .updatedAt(this.updatedAt)
            .isDeleted(this.isDeleted)
            .waterPeriod(this.waterPeriod)
            .build();
    }
}