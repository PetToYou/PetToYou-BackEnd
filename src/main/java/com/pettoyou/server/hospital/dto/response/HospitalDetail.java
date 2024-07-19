package com.pettoyou.server.hospital.dto.response;

import com.pettoyou.server.constant.enums.BaseStatus;
import com.pettoyou.server.hospital.entity.Hospital;
import com.pettoyou.server.store.dto.response.BusinessHourDto;
import com.pettoyou.server.store.dto.response.StorePhotoDto;
import com.pettoyou.server.store.entity.Address;
import com.pettoyou.server.store.entity.RegistrationInfo;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.List;

@Builder
public record HospitalDetail(
        @NotNull Long hospitalId,
        @NotNull String hospitalName,
        String storePhone,
        String notice,
        String websiteLink,
        String additionalServiceTag,
        String storeInfo,
        String storeInfoPhoto,
        Address address,
        List<BusinessHourDto> businessHours,
        List<StorePhotoDto> storePhotos,
        RegistrationInfo registrationInfo,
        HospitalTagDto hospitalTags
) {
    public static HospitalDetail from(Hospital hospital) {
        return HospitalDetail.builder()
                .hospitalId(hospital.getStoreId())
                .hospitalName(hospital.getStoreName())
                .notice(hospital.getNotice())
                .websiteLink(hospital.getWebsiteLink())
                .additionalServiceTag(hospital.getAdditionalServiceTag())
                .storeInfo(hospital.getStoreInfo())
                .storeInfoPhoto(hospital.getStoreInfoPhoto() == null ? "test.jpg" : hospital.getStoreInfoPhoto().getPhotoUrl())
                .address(hospital.getAddress())
                .businessHours(hospital.getBusinessHours().stream().map(BusinessHourDto::toDto).toList())
                .storePhotos(hospital.getStorePhotos().stream().map(StorePhotoDto::toDto).toList())
                .registrationInfo(hospital.getRegistrationInfo())
                .hospitalTags(HospitalTagDto.toDto(hospital.getTags()))
                .build();
    }
}
