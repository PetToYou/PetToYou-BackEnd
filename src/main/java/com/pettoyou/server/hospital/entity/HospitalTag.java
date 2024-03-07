package com.pettoyou.server.hospital.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "hospital_tag")
public class HospitalTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hospitalTagId;

    private String tagContent; // Service, BusinessHour, Specialities
    private String tagType; // SERVICE, BUSINESSHOUR, SPECIALITIES

}