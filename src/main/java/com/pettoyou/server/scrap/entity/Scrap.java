package com.pettoyou.server.scrap.entity;

import com.pettoyou.server.constant.entity.BaseEntity;
import com.pettoyou.server.member.entity.Member;
import com.pettoyou.server.store.entity.Store;
import com.pettoyou.server.store.entity.enums.StoreType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "scrap")
public class Scrap extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "scrap_id")
    private Long scrapId;


    @Enumerated(EnumType.STRING)
    private StoreType storeType;


    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

}