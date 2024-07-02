package com.pettoyou.server.hospital.dto;

import com.pettoyou.server.constant.enums.BaseStatus;
import com.pettoyou.server.hospital.entity.Hospital;
import com.pettoyou.server.store.dto.BusinessHourDtos;
import com.pettoyou.server.store.dto.RegistrationInfoDto;
import com.pettoyou.server.store.dto.StorePhotoDtos;
import com.pettoyou.server.store.entity.Address;
import com.pettoyou.server.store.entity.RegistrationInfo;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HospitalDto {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
        // Todo : HospitalRegisInfo 로 Refactoring -> record 타입 쓰는게 더 좋은듯함

        @NotNull
        private String hospitalName;

        @NotNull
        private String hospitalPhone;

        private String notice;

        private String additionalServiceTag;

        private String websiteLink;
        private String hospitalInfo;
        //        private String hospitalInfoPhoto; -> s3 이미지
        @NotNull
        private String zipCode;
        @NotNull
        private String sido;
        @NotNull
        private String sigungu;

        private String eupmyun;
        @NotNull
        private String doro;

        @NotNull
        private double longitude;
        @NotNull
        private double latitude;


        private List<BusinessHourDtos> businessHours;

        private RegistrationInfoDto.Request registrationInfo;

//        public static HospitalDto.Request toEntity(HospitalDto.Request hospitalDto)
    }


    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        // Todo : HospitalDetail 로 Refactoring 하는중
        // 병원 상세정보
        @NotNull
        private Long hospitalId;
        @NotNull
        private String hospitalName;
        private String storePhone;
        private String notice;
        private String websiteLink;
        private String additionalServiceTag;
        private String storeInfo;
        private String storeInfoPhoto;
        private BaseStatus storeStatus;
        private Address address;

        //
        @Builder.Default
        private List<BusinessHourDtos.Response> businessHours = new ArrayList<>();

        @Builder.Default
        private List<StorePhotoDtos> storePhotos = new ArrayList<>();
//    //페이징


        //    private List<Review> reviews = new ArrayList<>();
//    //페이징
        private RegistrationInfo registrationInfo;


        public static HospitalDto.Response toHospitalDto(Hospital hospital) {
            return HospitalDto.Response
                    .builder()
                    .hospitalId(hospital.getStoreId())
                    .hospitalName(hospital.getStoreName())
                    .storePhone(hospital.getStorePhone())
                    .notice(hospital.getNotice())
                    .websiteLink(hospital.getWebsiteLink())
                    .additionalServiceTag(hospital.getAdditionalServiceTag())
                    .storeInfo(hospital.getStoreInfo())
                    .storeStatus(hospital.getStoreStatus())
                    .storeInfoPhoto(hospital.getStoreInfoPhoto())
                    .address(hospital.getAddress())
                    .businessHours(
                            hospital
                                    .getBusinessHours()
                                    .stream()
                                    .map(BusinessHourDtos.Response::toDto)
                                    .collect(Collectors.toList())
                    )
                    .storePhotos(
                            hospital
                                    .getStorePhotos()
                                    .stream()
                                    .map(StorePhotoDtos::toDto)
                                    .collect(Collectors.toList())
                    )
                    .registrationInfo(hospital.getRegistrationInfo())
                    .build();
        }


    }


}
