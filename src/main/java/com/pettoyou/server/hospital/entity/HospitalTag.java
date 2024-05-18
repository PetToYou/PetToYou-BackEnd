//package com.pettoyou.server.hospital.entity;
//
//import com.pettoyou.server.store.entity.TagMapper;
//import jakarta.persistence.*;
//import lombok.*;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Getter
//@Entity
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@AllArgsConstructor
//@Builder
//@Table(name = "hospital_tag")
//public class HospitalTag {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long hospitalTagId;
//
//    private String tagType; // SERVICE, BUSINESSHOUR, SPECIALITIES
//
//    private String tagContent; // Service, BusinessHour, Specialities
//
//    @OneToMany(mappedBy = "hospitalTag")
//    private List<TagMapper> hospitalList = new ArrayList<>();
//
//
//}