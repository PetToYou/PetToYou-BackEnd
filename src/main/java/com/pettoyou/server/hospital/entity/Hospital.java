package com.pettoyou.server.hospital.entity;


import com.pettoyou.server.constant.enums.BaseStatus;
import com.pettoyou.server.review.entity.Review;
import com.pettoyou.server.store.dto.BusinessHourDto;
import com.pettoyou.server.store.dto.RegistrationInfoDto;
import com.pettoyou.server.store.entity.*;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;
import java.util.stream.Collectors;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@DiscriminatorValue("H")
@OnDelete(action = OnDeleteAction.CASCADE)
// store를 soft-delete 했을때 상속 객체인 hospital도 똑같이 soft-delete 해주도록 설정
@Table(name = "hospital")
public class Hospital extends Store {

    private String additionalServiceTag;

//    @OneToMany(mappedBy = "hospital", fetch = FetchType.LAZY)
//    private List<TagMapper> tags = new ArrayList<>();

    //순환참조 문제 발생.



    @Builder
    public Hospital(Long storeId, String storeName, String storePhone, String thumbnailUrl, String notice, Address address,
                    String websiteLink, BaseStatus storeStatus, String storeInfo, String storeInfoPhoto, String additionalServiceTag, RegistrationInfo registrationInfo, List<BusinessHour> businessHours, List<Review> reviews, List<StorePhoto> storePhotos
    ) {
        super(storeId, storeName, storePhone, thumbnailUrl,  notice, address, websiteLink, storeStatus, storeInfo, storeInfoPhoto, registrationInfo, businessHours, reviews, storePhotos);
        this.additionalServiceTag = additionalServiceTag;
    }


}

