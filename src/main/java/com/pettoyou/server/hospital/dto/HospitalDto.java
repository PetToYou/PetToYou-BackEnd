package com.pettoyou.server.hospital.dto;

import com.pettoyou.server.constant.enums.BaseStatus;
import com.pettoyou.server.hospital.entity.Hospital;
import com.pettoyou.server.store.dto.AddressDto;
import com.pettoyou.server.store.dto.BusinessHourDto;
import com.pettoyou.server.store.dto.RegistrationInfoDto;
import com.pettoyou.server.store.dto.StorePhotoDto;
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




public class HospitalDto{

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {

        @NotNull
        private String hospitalName;

        @NotNull
        private String hospitalPhone;

        private String notice;

        private String additionalServiceTag;

        private String websiteLink;
        private String hospitalInfo;

        private String thumbnailUrl;
        private String storeInfo;
        private String storeInfoPhoto;

//        private String hospitalInfoPhoto; -> s3 이미지

        @NotNull
        private AddressDto address;


        private List<BusinessHourDto.Request> businessHours;

        private RegistrationInfoDto.Request registrationInfo;

//        public static HospitalDto.Request toEntity(HospitalDto.Request hospitalDto)
    }








    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        // 병원 상세정보

        @NotNull
        private Long hospitalId;


        @NotNull
        private String hospitalName;

        private String storePhone;
        private String notice;
        //@URL(protocol = "", host = "")
        private String websiteLink;
        private String additionalServiceTag;
        private String storeInfo;
        private String storeInfoPhoto;
        private BaseStatus storeStatus;
        private Address address;

        //
        @Builder.Default
        private List<BusinessHourDto.Response> businessHours = new ArrayList<>();

        @Builder.Default
        private List<StorePhotoDto> storePhotos = new ArrayList<>();
//    //페이징


        //    private List<Review> reviews = new ArrayList<>();
//    //페이징
        private RegistrationInfoDto.Response registrationInfo;


        public static HospitalDto.Response toHospitalDto(Hospital hospital){
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
                                    .map(BusinessHourDto.Response::toDto)
                                    .collect(Collectors.toList())
                    )
                    .storePhotos(
                            hospital
                                    .getStorePhotos()
                                    .stream()
                                    .map(StorePhotoDto::toDto)
                                    .collect(Collectors.toList())
                    )
                    .registrationInfo(RegistrationInfoDto.Response.toRegistrationInfoDto(hospital.getRegistrationInfo()))
                    .build();
        }


    }


}
