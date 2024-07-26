package com.pettoyou.server.hospital.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.pettoyou.server.constant.enums.BaseStatus;
import com.pettoyou.server.hospital.dto.HospitalTagDto;
import com.pettoyou.server.hospital.entity.Hospital;
import com.pettoyou.server.hospital.entity.HospitalTag;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
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
        RegistrationInfo registrationInfo,
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
                //아예 제외 처리 하고 싶은데
                .address(hospital.getAddress())
                .businessHours(businessHours)
                .registrationInfo(hospital.getRegistrationInfo())
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
                //storeInfoPhoto가 nul인데 getPhotoUrl을 하면 에러가 발생하므로 null이라고 미리 걸러준다. -> jsonInclude.nonNull을 통해 null 걸러줌.
                .address(hospital.getAddress())
                .registrationInfo(hospital.getRegistrationInfo())
                .hospitalTags(HospitalTagDto.toDto(hospital.getTags()))
                .build();
    }
}
