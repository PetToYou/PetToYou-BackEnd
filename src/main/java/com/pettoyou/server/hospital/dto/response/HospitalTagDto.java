package com.pettoyou.server.hospital.dto.response;


import com.pettoyou.server.hospital.entity.TagMapper;
import com.pettoyou.server.hospital.entity.enums.HospitalTagType;
import lombok.Builder;

import java.util.ArrayList;
import java.util.List;

import static com.pettoyou.server.hospital.entity.enums.HospitalTagType.*;

@Builder
public record HospitalTagDto(
        List<String> services,
        List<String> businessHours,
        List<String> specialities,
        String emergency) {
    public static HospitalTagDto toDto(List<TagMapper> hospitalTags) {
        List<String> serviceList = new ArrayList<>();
        List<String> businessHourList = new ArrayList<>();
        List<String> specialitiesList = new ArrayList<>();
        String emergency = "";

        for (TagMapper hospitalTagMappers : hospitalTags) {
            HospitalTagType tagType = hospitalTagMappers.getHospitalTag().getTagType();

            if (tagType.equals(SERVICE)) serviceList.add(hospitalTagMappers.getHospitalTag().getTagContent());
            else if (tagType.equals(BUSINESSHOUR)) businessHourList.add(hospitalTagMappers.getHospitalTag().getTagContent());
            else if (tagType.equals(SPECIALITIES)) specialitiesList.add(hospitalTagMappers.getHospitalTag().getTagContent());
            else if (tagType.equals(EMERGENCY)) emergency = hospitalTagMappers.getHospitalTag().getTagContent();
        }

        return HospitalTagDto.builder()
                .services(serviceList)
                .businessHours(businessHourList)
                .specialities(specialitiesList)
                .emergency(emergency)
                .build();
    }
}
