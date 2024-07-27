package com.pettoyou.server.hospital.dto;


import com.pettoyou.server.hospital.entity.Hospital;
import com.pettoyou.server.hospital.entity.HospitalTag;
import com.pettoyou.server.hospital.entity.TagMapper;
import com.pettoyou.server.hospital.entity.enums.HospitalTagType;
import lombok.Builder;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.pettoyou.server.hospital.entity.enums.HospitalTagType.*;

@Builder
public record HospitalTagDto(
        //Response
        List<String> services,
        List<String> businessHours,
        List<String> specialities,
        List<String> emergency) {
    public static HospitalTagDto toDto(List<TagMapper> tagMappers) {
        Map<HospitalTagType, List<String>> result = tagMappers.stream()
                .collect(Collectors.groupingBy(mapper -> mapper.getHospitalTag().getTagType()
                        , Collectors.mapping(mapper -> mapper.getHospitalTag().getTagContent(), Collectors.toList())

                ));
        List<String> serviceList = result.get(HospitalTagType.SERVICE);
        List<String> businessHourList = result.get(BUSINESSHOUR);
        List<String> specialitiesList = result.get(SPECIALITIES);
        List<String> emergency = result.get(EMERGENCY);

        return HospitalTagDto.builder()
                .services(serviceList)
                .businessHours(businessHourList)
                .specialities(specialitiesList)
                .emergency(emergency)
                .build();
    }

    public static HospitalTagDto toDtoFromTags(List<HospitalTag> tags) {
        Map<HospitalTagType, List<String>> result =
                Optional.ofNullable(tags)
                        .orElse(Collections.emptyList())
                        .stream()
                        .collect(Collectors.groupingBy(HospitalTag::getTagType
                                , Collectors.mapping(HospitalTag::getTagContent, Collectors.toList())
                        ));

        return HospitalTagDto.builder()
                .services(result.get(SERVICE))
                .businessHours(result.get(BUSINESSHOUR))
                .specialities(result.get(SPECIALITIES))
                .emergency(result.get(EMERGENCY))
                .build();
    }


    public static List<TagMapper> toEntity(Hospital hospital, List<HospitalTag> tags) {

        if (tags.isEmpty()) {
            throw new IllegalArgumentException("No tags");
        }
        List<TagMapper> tagMapperList =
                tags.stream().map(tag -> TagMapper.builder()
                        .hospitalTag(tag)
                        .hospital(hospital)
                        .build()
                ).toList();


        hospital.getTags().addAll(tagMapperList);
        //병원 객체에 저장
        return tagMapperList;

    }

}