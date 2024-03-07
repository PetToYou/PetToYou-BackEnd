package com.pettoyou.server.insurance.entity;

import com.pettoyou.server.constant.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "insurance")
public class Insurance extends BaseEntity {


    @Id
    @GeneratedValue
    private Long id;

    private String insuranceName;
    private String insuranceCompany;
    private String price;

}