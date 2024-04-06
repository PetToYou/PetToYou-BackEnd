package com.pettoyou.server.hospital.dto;

import com.pettoyou.server.constant.enums.BaseStatus;
import com.pettoyou.server.review.entity.Review;
import com.pettoyou.server.store.entity.Address;
import com.pettoyou.server.store.entity.BusinessHour;
import com.pettoyou.server.store.entity.RegistrationInfo;
import com.pettoyou.server.store.entity.StorePhoto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HospitalDto {

    private Long hospitalId;

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
//    private List<BusinessHour> businessHours = new ArrayList<>();
//    private List<StorePhoto> storePhotos = new ArrayList<>();
//    //페이징
//    private List<Review> reviews = new ArrayList<>();
//    //페이징
//    private RegistrationInfo registrationInfo;


}
