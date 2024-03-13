package com.pettoyou.server.insurance.entity;

import com.pettoyou.server.constant.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "insurance")
public class Insurance extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long insuraceId;

    private String insuranceName;
    private String insuranceCompany;
    private String price;

}