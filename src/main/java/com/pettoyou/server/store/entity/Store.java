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
public abstract class Store extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long storeId;

    @OneToMany(mappedBy = "store", fetch = FetchType.LAZY)
    private List<StorePhoto> storePhotos = new ArrayList<>();

    @OneToMany(mappedBy = "store", fetch=FetchType.LAZY)
    private List<Reserve> reserves = new ArrayList<>();

    @OneToMany(mappedBy = "store", fetch = FetchType.LAZY)
    private List<Review> reviews = new ArrayList<>();

    @OneToOne(mappedBy = "store")
    private RegistrationInfo registrationInfo;


    @Embedded
    private Address address;

}
