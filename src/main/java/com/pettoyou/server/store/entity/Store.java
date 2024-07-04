package com.pettoyou.server.store.entity;


import com.pettoyou.server.constant.entity.BaseEntity;
import com.pettoyou.server.constant.enums.BaseStatus;
import com.pettoyou.server.review.entity.Review;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE store SET store_status = 'DEACTIVATE' WHERE store_id=?")
@SQLRestriction("store_status = 'ACTIVATE'")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn // 하위 테이블의 구분 컬럼 생성
@Table(name = "store", indexes = {
        @Index(name = "idx_store_name", columnList = "storeName")
})
public abstract class Store extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    private Long storeId;

    @NotNull
    @Size(min = 2)
    @Column(nullable = false)
    private String storeName;

    private String storePhone;


    private String thumbnailUrl;

    private String notice;



    private String websiteLink;

    @Column(columnDefinition = "TEXT")
    private String storeInfo;

    private String storeInfoPhoto;





    @Enumerated(EnumType.STRING)
    private BaseStatus storeStatus=BaseStatus.ACTIVATE;

    @Embedded
    private Address address;




    @OneToMany(mappedBy = "store", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<BusinessHour> businessHours = new ArrayList<>();


    @OneToMany(mappedBy = "store", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<StorePhoto> storePhotos = new ArrayList<>();

//    @OneToMany(mappedBy = "store", fetch = FetchType.LAZY)
//    private List<Reserve> reserves = new ArrayList<>();

    @OneToMany(mappedBy = "store", fetch = FetchType.LAZY)
    private List<Review> reviews = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "registration_info_id")
    private RegistrationInfo registrationInfo;



    protected Store(Long storeId, String storeName, String storePhone, String thumbnailUrl, String notice, Address address,
                 String websiteLink, BaseStatus storeStatus, String storeInfo, String storeInfoPhoto,
                 RegistrationInfo registrationInfo, List<BusinessHour> businessHours, List<Review> reviews, List<StorePhoto> storePhotos) {
        this.storeId = storeId;
        this.storeName = storeName;
        this.storePhone = storePhone;
        this.thumbnailUrl = thumbnailUrl;
        this.notice = notice;
        this.address = address;
        this.websiteLink = websiteLink;
        this.storeStatus = storeStatus;
        this.storeInfo = storeInfo;
        this.storeInfoPhoto = storeInfoPhoto;
        this.registrationInfo = registrationInfo;
        this.businessHours = businessHours;
        this.reviews = reviews;
        this.storePhotos = storePhotos;
    }





}
