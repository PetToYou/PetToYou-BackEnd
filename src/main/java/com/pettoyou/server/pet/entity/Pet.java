package com.pettoyou.server.pet.entity;

import com.pettoyou.server.constant.entity.BaseEntity;
import com.pettoyou.server.constant.enums.BaseStatus;
import com.pettoyou.server.member.entity.Member;
import com.pettoyou.server.pet.dto.PetDto;
import com.pettoyou.server.pet.entity.enums.PetType;
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
    @Column(name = "pet_id")
    private Long petId;

    @Column(nullable = false)
    private String petName;

    @Column(nullable = false)
    private String species;

    @Column(nullable = false)
    private LocalDate birth;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PetType petType;

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
    @JoinColumn(name = "member_id")
    private Member member;

    public static Pet toEntity(PetDto.Request.Register registerDto, Member member) {
        return builder()
                .petName(registerDto.getName())
                .species(registerDto.getSpecies())
                .birth(registerDto.getBirth())
                .petType(registerDto.getPetType().equals("DOG") ? PetType.DOG : PetType.CAT)
                .adoptionDate(registerDto.getAdoptionDate() == null ? null : registerDto.getAdoptionDate())
                .petMedicalInfo(PetMedicalInfo.toPetMedicalInfo(registerDto.getPetMedicalInfo()))
                .member(member)
                .petStatus(BaseStatus.ACTIVATE)
                .build();
    }

    public void modify(PetDto.Request.Register modifyDto) {
        this.petName = modifyDto.getName();
        this.species = modifyDto.getSpecies();
        this.birth = modifyDto.getBirth();
        this.petType = modifyDto.getPetType().equals("DOG") ? PetType.DOG : PetType.CAT;
        this.adoptionDate = modifyDto.getAdoptionDate();
        this.petMedicalInfo = PetMedicalInfo.toPetMedicalInfo(modifyDto.getPetMedicalInfo());
    }
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
