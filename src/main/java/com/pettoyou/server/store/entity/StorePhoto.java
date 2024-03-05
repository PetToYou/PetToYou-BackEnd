package com.pettoyou.server.store.entity;

import com.pettoyou.server.constant.enums.BaseStatus;
import com.pettoyou.server.store.StoreType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "store_photo")
public class StorePhoto {

    @Id
    @GeneratedValue
    private String storePhotoId;

    private StoreType storeType;

    private String storePhotoUrl;

    private Integer photoOrder;

    private BaseStatus photoStatus;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

}
