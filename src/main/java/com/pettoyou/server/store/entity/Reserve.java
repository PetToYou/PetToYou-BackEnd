package com.pettoyou.server.store.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "reserve")
public class Reserve {

    @Id
    @GeneratedValue
    private Long reserveId;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;



}