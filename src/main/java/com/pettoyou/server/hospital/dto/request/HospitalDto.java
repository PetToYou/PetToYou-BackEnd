package com.pettoyou.server.hospital.dto.request;

import com.pettoyou.server.hospital.entity.Hospital;
import com.pettoyou.server.photo.entity.PhotoData;
import com.pettoyou.server.store.dto.request.AddressDto;
import com.pettoyou.server.store.dto.BusinessHourDto;
import com.pettoyou.server.store.dto.RegistrationInfoDto;
import com.pettoyou.server.store.entity.enums.StoreType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Builder
public record HospitalDto(
        @NotNull @Size(min = 2, max = 20) String hospitalName,
        @NotNull @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$") String hospitalPhone,
        String notice,
        String additionalServiceTag,
        String websiteLink,
        String storeInfo,
        @NotNull AddressDto address,
        List<Long> tagIdList,
        List<BusinessHourDto.Request> businessHours,
        RegistrationInfoDto.Request registrationInfo
) {
    public static Hospital toHospitalEntity(HospitalDto hospitalDto, PhotoData thumbnail) {
        if (hospitalDto == null) {
            throw new IllegalArgumentException("HospitalDto is null");
        }
        if (thumbnail == null) {
            throw new IllegalArgumentException("Thumbnail is null");
        }

        Hospital hospital = Hospital.builder()
                .additionalServiceTag(hospitalDto.additionalServiceTag())
                .storeName(hospitalDto.hospitalName())
                .storePhone(hospitalDto.hospitalPhone())
                .notice(hospitalDto.notice())
                .websiteLink(hospitalDto.websiteLink())
                .address(AddressDto.toEntity(hospitalDto.address()))
                .storeInfo(hospitalDto.storeInfo())
                .thumbnail(thumbnail)
                .storePhotos(new ArrayList<>())
                .businessHours(new ArrayList<>())
                .registrationInfo(RegistrationInfoDto.Request.toEntity(hospitalDto.registrationInfo(), StoreType.HOSPITAL))
                .build();

        hospital.getBusinessHours()
                .addAll(hospitalDto.businessHours()
                        .stream()
                        .map(businessHour -> BusinessHourDto.Request.toEntity(businessHour, hospital))
                        .collect(Collectors.toList()));

        return hospital;
    }

    public static Hospital toHospitalEntity(HospitalDto hospitalDto, PhotoData thumbnail, PhotoData storeInfoPhoto) {
        if (hospitalDto == null) {
            throw new IllegalArgumentException("HospitalDto is null");
        }
        if (thumbnail == null) {
            throw new IllegalArgumentException("Thumbnail is null");
        }

        Hospital hospital = Hospital.builder()
                .additionalServiceTag(hospitalDto.additionalServiceTag())
                .storeName(hospitalDto.hospitalName())
                .storePhone(hospitalDto.hospitalPhone())
                .notice(hospitalDto.notice())
                .websiteLink(hospitalDto.websiteLink())
                .address(AddressDto.toEntity(hospitalDto.address()))
                .storeInfo(hospitalDto.storeInfo())
                .storeInfoPhoto(storeInfoPhoto)
                .thumbnail(thumbnail)
                .storePhotos(new ArrayList<>())
                .businessHours(new ArrayList<>())
                .registrationInfo(RegistrationInfoDto.Request.toEntity(hospitalDto.registrationInfo(), StoreType.HOSPITAL))
                .build();

        hospital.getBusinessHours()
                .addAll(hospitalDto.businessHours()
                        .stream()
                        .map(businessHour -> BusinessHourDto.Request.toEntity(businessHour, hospital))
                        .collect(Collectors.toList()));

        return hospital;
    }
}
