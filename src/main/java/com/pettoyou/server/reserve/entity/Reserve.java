package com.pettoyou.server.reserve.entity;

import com.pettoyou.server.constant.entity.BaseEntity;
import com.pettoyou.server.constant.enums.BaseStatus;
import com.pettoyou.server.member.entity.Member;
import com.pettoyou.server.pet.entity.Pet;
import com.pettoyou.server.store.entity.Store;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "reserve")
public class Reserve extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reserveId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storeId")
    private Store store;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "petId")
    private Pet pet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    private Member member;

    private LocalDateTime reserveTime;
    //Date와 Time 분리????

    @Enumerated(EnumType.STRING)
    private BaseStatus reserveStatus;

}