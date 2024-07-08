package com.pettoyou.server.hospital.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pettoyou.server.hospital.entity.enums.HospitalTagType;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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