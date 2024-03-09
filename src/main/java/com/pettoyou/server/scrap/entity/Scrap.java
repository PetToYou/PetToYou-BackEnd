package com.pettoyou.server.scrap.entity;

import com.pettoyou.server.constant.entity.BaseEntity;
import com.pettoyou.server.member.entity.Member;
import com.pettoyou.server.store.entity.Store;
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
    private Long ScrapId;

    @ManyToOne
    @JoinColumn(name = "memberId")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "storeId")
    private Store store;

}