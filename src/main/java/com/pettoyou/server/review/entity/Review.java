package com.pettoyou.server.review.entity;

import com.pettoyou.server.constant.entity.BaseEntity;
import com.pettoyou.server.constant.enums.BaseStatus;
import com.pettoyou.server.member.entity.Member;
import com.pettoyou.server.pet.entity.Pet;
import com.pettoyou.server.store.entity.enums.StoreType;
import com.pettoyou.server.store.entity.Store;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "review")
public class Review extends BaseEntity {

    @Id
    @GeneratedValue
    private Long reviewId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;


    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "pet_id")
    private Pet pet;

    private StoreType storeType;

    private float rating;
    private String content;

    @Enumerated(EnumType.STRING)
    private BaseStatus reviewStatus;
}
//ReviewId PK long
//StoreId long FK >- Hospital.HospitalId
//MemberId long FK >- Member.MemberId
//PetId long FK >- Pet.PetId
//StoreType string
//Rating float
//Content text
//CreatedAt datetime
//ReviewStatus string # ACTIVATE, DEACTIVATE,
//
