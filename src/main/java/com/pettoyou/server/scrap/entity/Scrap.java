package com.pettoyou.server.scrap.entity;

import com.pettoyou.server.member.entity.Member;
import com.pettoyou.server.store.entity.Store;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "like")
public class Scrap {
    @Id
    @GeneratedValue
    private Long ScrapId;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

}