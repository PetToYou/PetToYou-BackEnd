package com.pettoyou.server.store.entity;

import com.pettoyou.server.store.entity.enums.StoreType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "registration_info")
public class RegistrationInfo  {


    @Id
    @GeneratedValue
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