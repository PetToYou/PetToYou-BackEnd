package com.pettoyou.server.hospital.entity;

import com.pettoyou.server.hospital.entity.enums.HospitalTagType;
import jakarta.persistence.*;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;

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

    // SERVICE, BUSINESSHOUR, SPECIALITIES, EMERGENCY
    @Enumerated(EnumType.STRING)
    private HospitalTagType tagType;

    // Service, BusinessHour, Specialities, Emergency
    private String tagContent;

    @OneToMany(mappedBy = "hospitalTag")
    @JsonIgnore()
    private List<TagMapper> hospitalList = new ArrayList<>();

}