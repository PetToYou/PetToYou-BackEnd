package com.pettoyou.server.store.entity;


import com.pettoyou.server.constant.entity.BaseEntity;
import com.pettoyou.server.reserve.entity.Reserve;
import com.pettoyou.server.review.entity.Review;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn // 하위 테이블의 구분 컬럼 생성
public abstract class Store extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long storeId;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "store")
    private List<BusinessHour> businessHours = new ArrayList<>();

    @OneToMany(mappedBy = "store", fetch = FetchType.LAZY)
    private List<StorePhoto> storePhotos = new ArrayList<>();

    @OneToMany(mappedBy = "store", fetch = FetchType.LAZY)
    private List<Reserve> reserves = new ArrayList<>();

    @OneToMany(mappedBy = "store", fetch = FetchType.LAZY)
    private List<Review> reviews = new ArrayList<>();

    @OneToOne(mappedBy = "store")
    private RegistrationInfo registrationInfo;


    @OneToMany(mappedBy = "store")
    private List<TagMapper> tags = new ArrayList<>();


}
