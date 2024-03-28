package com.pettoyou.server.review.entity;

import com.pettoyou.server.constant.entity.BaseEntity;
import com.pettoyou.server.constant.enums.BaseStatus;
import com.pettoyou.server.member.entity.Member;
import com.pettoyou.server.pet.entity.Pet;
import com.pettoyou.server.store.entity.enums.StoreType;
import com.pettoyou.server.store.entity.Store;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "review")
public class Review extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "review_id")
    private Long reviewId;

    @Enumerated(EnumType.STRING)
    private StoreType storeType;

    @Enumerated(EnumType.STRING)
    private BaseStatus reviewStatus;



    private double rating;

    private String content;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id")
    private Pet pet;


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
