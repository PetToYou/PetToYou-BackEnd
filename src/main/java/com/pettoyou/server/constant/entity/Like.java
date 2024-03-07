package com.pettoyou.server.constant.entity;

import com.pettoyou.server.member.entity.Member;
import com.pettoyou.server.store.entity.Store;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "like")
public class Like {
    @Id
    @GeneratedValue
    private Long LikeId;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

}