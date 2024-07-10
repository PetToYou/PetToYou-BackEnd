package com.pettoyou.server.hospital.entity;

import com.pettoyou.server.hospital.entity.enums.HospitalTagType;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "hospital_tag")
public class HospitalTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hospitalTagId;

    @Enumerated(EnumType.STRING)
    private HospitalTagType tagType;

    // Service, BusinessHour, Specialities, Emergency
    private String tagContent;
}