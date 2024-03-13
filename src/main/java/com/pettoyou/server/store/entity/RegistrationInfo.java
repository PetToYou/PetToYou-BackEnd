package com.pettoyou.server.store.entity;

import com.pettoyou.server.store.entity.enums.StoreType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "registration_info")
public class RegistrationInfo  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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