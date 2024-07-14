package com.pettoyou.server.hospital.dto;


import com.pettoyou.server.hospital.entity.Hospital;
import com.pettoyou.server.hospital.entity.HospitalTag;
import com.pettoyou.server.hospital.entity.TagMapper;
import com.pettoyou.server.hospital.entity.enums.HospitalTagType;
import lombok.Builder;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.pettoyou.server.hospital.entity.enums.HospitalTagType.*;

@Builder
public record HospitalTagDto(
        //Response
        List<String> services,
        List<String> businessHours,
        List<String> specialities,
        List<String> emergency) {
    public static HospitalTagDto toDto(List<TagMapper> TagMappers)
    {
        Map<HospitalTagType, List<String>> result = TagMappers.stream()
                .collect(Collectors.groupingBy(mapper -> mapper.getHospitalTag().getTagType()
                ,Collectors.mapping(mapper -> mapper.getHospitalTag().getTagContent(), Collectors.toList())
                ));
        // 각 Type 별 분류를 위해 Map으로 만들기. groupingBy 메소드 공부하기
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

    public static List<TagMapper> toEntity(Hospital hospital, List<HospitalTag> tags){

        if(tags.isEmpty())
        {
            throw new IllegalArgumentException("No tags");
        }
        List<TagMapper> tagMapperList =
                tags.stream().map(tag -> TagMapper.builder()
                        .hospitalTag(tag)
                        .hospital(hospital)
                        .build()
                ).collect(Collectors.toList());


        hospital.getTags().addAll(tagMapperList);
        //병원 객체에 저장
        return tagMapperList;

    }

}