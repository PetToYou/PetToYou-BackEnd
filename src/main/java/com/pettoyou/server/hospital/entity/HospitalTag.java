package com.pettoyou.server.hospital.entity;

import com.pettoyou.server.hospital.entity.enums.HospitalTagType;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "hospital_tag")
public class HospitalTag {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hospitalTagId;

    @Enumerated(EnumType.STRING)
    private HospitalTagType tagType;

    // Service, BusinessHour, Specialities, Emergency
    private String tagContent;

    public static List<TagMapper> toEntity(Hospital hospital, List<HospitalTag> tags) {

        if (tags.isEmpty()) {
            throw new IllegalArgumentException("No tags");
        }
        return tags.stream().map(tag -> TagMapper.builder()
                .hospitalTag(tag)
                .hospital(hospital)
                .build()
        ).toList();
        //collect.toList와 차이점은 null 일 경우 throw exception

    }
}