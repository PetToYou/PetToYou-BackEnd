package com.pettoyou.server.store.entity;


import com.pettoyou.server.constant.entity.BaseEntity;
import com.pettoyou.server.constant.enums.BaseStatus;
import com.pettoyou.server.review.entity.Review;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
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

    private String storeInfo;

    private String storeInfoPhoto;





    @Enumerated(EnumType.STRING)
    private BaseStatus storeStatus;

    @Embedded
    private Address address;



    @OneToMany(mappedBy = "store", fetch = FetchType.LAZY)
    private List<BusinessHour> businessHours = new ArrayList<>();

    @OneToMany(mappedBy = "store", fetch = FetchType.LAZY)
    private List<StorePhoto> storePhotos = new ArrayList<>();

//    @OneToMany(mappedBy = "store", fetch = FetchType.LAZY)
//    private List<Reserve> reserves = new ArrayList<>();

    @OneToMany(mappedBy = "store", fetch = FetchType.LAZY)
    private List<Review> reviews = new ArrayList<>();

    @OneToOne(mappedBy = "store", fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    private RegistrationInfo registrationInfo;







}
