package com.pettoyou.server.pet.entity;

import com.pettoyou.server.constant.entity.BaseEntity;
import com.pettoyou.server.constant.enums.BaseStatus;
import com.pettoyou.server.member.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "pet")
public class Pet extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String petId;

    private String petName;
    private String species;
    private Integer age;
    private LocalDate birth;
    private LocalDate adoptionDate;
    private boolean isSharing;
    private BaseStatus petStatus;

    @OneToMany(mappedBy = "pet",fetch = FetchType.LAZY)
    private List<PetProfilePhoto> petProfilePhotos = new ArrayList<>();

    @Embedded
    private PetMedicalInfo petMedicalInfo;

    @Embedded
    private PetSharingInfo petSharingInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
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
