package com.pettoyou.server.pet.entity;

import com.pettoyou.server.constant.entity.BaseEntity;
import com.pettoyou.server.constant.enums.BaseStatus;
import com.pettoyou.server.member.entity.Member;
import com.pettoyou.server.reserve.entity.Reserve;
import com.pettoyou.server.review.entity.Review;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "pet")
public class Pet extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long petId;

    private String petName;

    private String species;

    private Integer age;

    private LocalDate birth;

    private LocalDate adoptionDate;

    @Enumerated(EnumType.STRING)
    private BaseStatus petStatus;

    @Embedded
    private PetMedicalInfo petMedicalInfo;

    @OneToMany(mappedBy = "pet", fetch = FetchType.LAZY)
    private List<PetProfilePhoto> petProfilePhotos = new ArrayList<>();

    @OneToMany(mappedBy = "pet", fetch = FetchType.LAZY)
    private List<Reserve> reserves = new ArrayList<>();

    @OneToMany(mappedBy = "pet", fetch = FetchType.LAZY)
    private List<Review> reviews = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    private Member member;
}

//Pet
//-
//PetId PK IDENTITY
//MemberId long FK >- Member.MemberId
//Name string
//Species string
//Age int
//Birth LocalDateTime
//AdpotionDate LocalDateTime
//IsSharing boolean
//PetStatus string # ACTIVATE, DEACTIVATE,
