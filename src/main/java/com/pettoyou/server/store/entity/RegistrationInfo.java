package com.pettoyou.server.store.entity;

import com.pettoyou.server.constant.entity.BaseEntity;
import com.pettoyou.server.store.entity.enums.StoreType;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "registration_info")
public class RegistrationInfo extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "registration_info_id")
    private Long registrationInfoId;

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @Enumerated(EnumType.STRING)
    private StoreType storeType;

    private String ceoName;

    private String ceoPhone;

    private String ceoEmail;

    private String businessNumber;
}