package com.pettoyou.server.hospital.dto;

import com.pettoyou.server.constant.enums.BaseStatus;
import com.pettoyou.server.hospital.entity.Hospital;
import com.pettoyou.server.photo.entity.PhotoData;
import com.pettoyou.server.store.dto.request.AddressDto;
import com.pettoyou.server.store.dto.BusinessHourDto;
import com.pettoyou.server.store.dto.RegistrationInfoDto;
import com.pettoyou.server.store.dto.StorePhotoDto;
import com.pettoyou.server.store.entity.Address;
import com.pettoyou.server.store.entity.enums.StoreType;
import jakarta.persistence.Embedded;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
        @Size(min=2, max=20)
        private String hospitalName;

        @NotNull
        @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$")
        //하이픈 포함
        private String hospitalPhone;
        private String notice;
        private String additionalServiceTag;
        private String websiteLink;

//        private String thumbnailUrl;

        private String storeInfo;
        @Embedded
        private PhotoData storeInfoPhoto;
        @NotNull
        private AddressDto address;

        private List<Long> tagIdList;

        private List<BusinessHourDto.Request> businessHours;

        private RegistrationInfoDto.Request registrationInfo;


        public static Hospital toHospitalEntity(HospitalDto.Request hospitalDtoRequest, PhotoData thumbnail) {

            if (hospitalDtoRequest == null) {
                throw new IllegalArgumentException("HospitalDto.Request is null");
            }
            if (thumbnail == null) {
                throw new IllegalArgumentException("Thumbnail is null");
            }


            Hospital hospital = Hospital.builder()
                    .additionalServiceTag(hospitalDtoRequest.getAdditionalServiceTag())
                    .storeName(hospitalDtoRequest.getHospitalName())
                    .storePhone(hospitalDtoRequest.getHospitalPhone())
                    .notice(hospitalDtoRequest.getNotice())
                    .websiteLink(hospitalDtoRequest.getWebsiteLink())
                    .address(AddressDto.toEntity(hospitalDtoRequest.getAddress()))
                    .storeInfo(hospitalDtoRequest.getStoreInfo())
                    .thumbnail(thumbnail)
                    .storePhotos(new ArrayList<>())
                    .businessHours(new ArrayList<>())
                    .registrationInfo(RegistrationInfoDto.Request.toEntity(hospitalDtoRequest.getRegistrationInfo(), StoreType.HOSPITAL))
                    .build();

            //연관관계 설정.
            hospital.getBusinessHours()
                    .addAll(hospitalDtoRequest.getBusinessHours()
                            .stream()
                            .map(businessHours->BusinessHourDto.Request.toEntity(businessHours,hospital))//BusinessHour에 hospital에 담아줌
                            .collect(Collectors.toList()));

            return hospital;
        }
        public static Hospital toHospitalEntity(HospitalDto.Request hospitalDtoRequest, PhotoData thumbnail, PhotoData storeInfoPhoto) {

            if (hospitalDtoRequest == null) {
                throw new IllegalArgumentException("HospitalDto.Request is null");
            }

            if(thumbnail == null) {
                throw new IllegalArgumentException("Thumbnail is null");
            }
            Hospital hospital = Hospital.builder()
                    .additionalServiceTag(hospitalDtoRequest.getAdditionalServiceTag())
                    .storeName(hospitalDtoRequest.getHospitalName())
                    .storePhone(hospitalDtoRequest.getHospitalPhone())
                    .notice(hospitalDtoRequest.getNotice())
                    .websiteLink(hospitalDtoRequest.getWebsiteLink())
                    .address(AddressDto.toEntity(hospitalDtoRequest.getAddress()))
                    .storeInfo(hospitalDtoRequest.getStoreInfo())
                    .storeInfoPhoto(storeInfoPhoto)
                    .thumbnail(thumbnail)
                    .storePhotos(new ArrayList<>())
                    .businessHours(new ArrayList<>())
                    .registrationInfo(RegistrationInfoDto.Request.toEntity(hospitalDtoRequest.getRegistrationInfo(), StoreType.HOSPITAL))
                    .build();


            //연관관계 설정.
            hospital.getBusinessHours()
                    .addAll(hospitalDtoRequest.getBusinessHours()
                            .stream()
                            .map(businessHours->BusinessHourDto.Request.toEntity(businessHours,hospital))//BusinessHour에 hospital에 담아줌
                            .collect(Collectors.toList()));
            //Registration Info 추가



            return hospital;
        }

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
        @Embedded
        private PhotoData storeInfoPhoto;
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