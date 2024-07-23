package com.pettoyou.server.store.dto.response;

import com.pettoyou.server.hospital.entity.HospitalTag;
import com.pettoyou.server.hospital.entity.enums.HospitalTagType;
import lombok.Builder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Builder
public record TagInfo(
        List<String> services,
        List<String> businessHours,
        List<String> specialists,
        List<String> emergencies
) {
    public static TagInfo from(List<HospitalTag> hospitalTags) {
        Map<HospitalTagType, List<String>> tagMap = hospitalTags.stream()
                .collect(Collectors.groupingBy(
                        HospitalTag::getTagType,
                        Collectors.mapping(HospitalTag::getTagContent, Collectors.toList())
                ));

        return TagInfo.builder()
                .services(tagMap.getOrDefault(HospitalTagType.SERVICE, new ArrayList<>()))
                .businessHours(tagMap.getOrDefault(HospitalTagType.BUSINESSHOUR, new ArrayList<>()))
                .specialists(tagMap.getOrDefault(HospitalTagType.SPECIALITIES, new ArrayList<>()))
                .emergencies(tagMap.getOrDefault(HospitalTagType.EMERGENCY, new ArrayList<>()))
                .build();
    }
}
