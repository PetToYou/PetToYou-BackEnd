package com.pettoyou.server.hospital.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.pettoyou.server.constant.enums.BaseStatus;
import com.pettoyou.server.hospital.dto.HospitalTagDto;
import com.pettoyou.server.hospital.entity.Hospital;
import com.pettoyou.server.hospital.entity.HospitalTag;
import com.pettoyou.server.store.dto.RegistrationInfoDto;
import com.pettoyou.server.store.dto.response.BusinessHourDto;
import com.pettoyou.server.store.dto.response.StorePhotoDto;
import com.pettoyou.server.store.entity.Address;
import com.pettoyou.server.store.entity.BusinessHour;
import com.pettoyou.server.store.entity.RegistrationInfo;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Builder
public record HospitalDetail(
        @NotNull Long hospitalId,
        @NotNull String hospitalName,
        String storePhone,
        String thumbnailUrl,
        String notice,
        String websiteLink,
        String additionalServiceTag,
        String storeInfo,
        String storeInfoPhoto,
        Address address,
        List<Times> businessHours,
        RegistrationInfoDto.Response registrationInfo,
        HospitalTagDto hospitalTags
) {
    public static HospitalDetail from(Hospital hospital, List<HospitalTag> tagList) {
        List<Times> businessHours = Optional.ofNullable(hospital.getBusinessHours())
                .orElse(Collections.emptyList())
                .stream()
                .map(Times::of).toList();

        return HospitalDetail.builder()
                .hospitalId(hospital.getStoreId())
                .hospitalName(hospital.getStoreName())
                .thumbnailUrl(hospital.getThumbnail() ==null ? "default_url" : hospital.getThumbnail().getPhotoUrl())
                .storePhone(hospital.getStorePhone())
                .notice(hospital.getNotice())
                .websiteLink(hospital.getWebsiteLink())
                .additionalServiceTag(hospital.getAdditionalServiceTag())
                .storeInfo(hospital.getStoreInfo())
                .storeInfoPhoto(hospital.getStoreInfoPhoto() == null ? null : hospital.getStoreInfoPhoto().getPhotoUrl())
                .address(hospital.getAddress())
                .businessHours(businessHours)
                .registrationInfo(RegistrationInfoDto.Response.toDto(hospital.getRegistrationInfo()))
                .hospitalTags(HospitalTagDto.toDtoFromTags(tagList))
                .build();
    }

    public static HospitalDetail from(Hospital hospital) {
        return HospitalDetail.builder()
                .hospitalId(hospital.getStoreId())
                .hospitalName(hospital.getStoreName())
                .thumbnailUrl(hospital.getThumbnail() ==null ? "default_url" : hospital.getThumbnail().getPhotoUrl())
                .storePhone(hospital.getStorePhone())
                .notice(hospital.getNotice())
                .websiteLink(hospital.getWebsiteLink())
                .additionalServiceTag(hospital.getAdditionalServiceTag())
                .storeInfo(hospital.getStoreInfo())
                .storeInfoPhoto(hospital.getStoreInfoPhoto() == null ? null : hospital.getStoreInfoPhoto().getPhotoUrl())
                .address(hospital.getAddress())
                .registrationInfo(RegistrationInfoDto.Response.toDto(hospital.getRegistrationInfo()))
                .hospitalTags(HospitalTagDto.toDto(hospital.getTags()))
                .build();
    }
}
