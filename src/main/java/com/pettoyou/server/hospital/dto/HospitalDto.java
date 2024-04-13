package com.pettoyou.server.hospital.dto;

import com.pettoyou.server.constant.enums.BaseStatus;
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

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HospitalDto {

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


}
