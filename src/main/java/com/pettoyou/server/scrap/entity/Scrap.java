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
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "scrap")
public class Scrap extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "scrap_id")
    private Long scrapId;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    private Scrap(Member member, Store store) {
        this.member = member;
        this.store = store;
    }

    public static Scrap of(Member member, Store store) {
        return new Scrap(member, store);
    }
}