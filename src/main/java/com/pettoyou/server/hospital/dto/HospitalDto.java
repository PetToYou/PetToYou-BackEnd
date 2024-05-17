package com.pettoyou.server.hospital.dto;

import com.pettoyou.server.constant.enums.BaseStatus;
import com.pettoyou.server.hospital.entity.Hospital;
import com.pettoyou.server.store.dto.BusinessHourDto;
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

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HospitalDto {
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
   private List<BusinessHourDto> businessHours = new ArrayList<>();

    @Builder.Default
   private List<StorePhotoDto> storePhotos = new ArrayList<>();
//    //페이징


//    private List<Review> reviews = new ArrayList<>();
//    //페이징
    private RegistrationInfo registrationInfo;


    public static HospitalDto toHospitalDto(Hospital hospital){
        return HospitalDto
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
                                .map(BusinessHourDto::toDto)
                                .collect(Collectors.toList())
                )
                .storePhotos(
                        hospital
                                .getStorePhotos()
                                .stream()
                                .map(StorePhotoDto::toDto)
                                .collect(Collectors.toList())
                )
                .registrationInfo(hospital.getRegistrationInfo())
                .build();
    }


}
