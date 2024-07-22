package com.pettoyou.server.store.dto.response;

import com.pettoyou.server.hospital.entity.HospitalTag;
import com.pettoyou.server.hospital.entity.enums.HospitalTagType;
import lombok.Builder;

import java.util.ArrayList;
import java.util.List;

@Builder
public record TagInfo(
        List<String> services,
        List<String> businessHours,
        List<String> specialists,
        String emergencyStatus
) {
public static TagInfo from(List<HospitalTag> hospitalTags) {
    List<String> services = new ArrayList<>();
    List<String> businessHours = new ArrayList<>();
    List<String> specialists = new ArrayList<>();
    String emergencyStatus = "";

    for (HospitalTag hospitalTag : hospitalTags) {
        HospitalTagType tagType = hospitalTag.getTagType();
        String tagContent = hospitalTag.getTagContent();

        if (tagType.equals(HospitalTagType.SERVICE)) services.add(tagContent);
        else if (tagType.equals(HospitalTagType.BUSINESSHOUR)) businessHours.add(tagContent);
        else if (tagType.equals(HospitalTagType.SPECIALITIES)) specialists.add(tagContent);
        else {
            // record는 불변객체기 때문에 객체를 수정할시에 인스턴스를 새로 만들어줘야함.
            emergencyStatus = tagContent;
        }
    }

    return TagInfo.builder()
            .services(services)
            .businessHours(businessHours)
            .specialists(specialists)
            .emergencyStatus(emergencyStatus)
            .build();
}
}
