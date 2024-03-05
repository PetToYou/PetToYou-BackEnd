package com.pettoyou.server.banner.entity;

import com.pettoyou.server.banner.BannerType;
import com.pettoyou.server.constant.entity.BaseEntity;
import com.pettoyou.server.constant.enums.BaseStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "banner")
public class Banner extends BaseEntity {

    @Id
    @GeneratedValue
    private Long bannerId;
    private String bannerName;
    private String bannerImg;
    private String bannerLink;

    @Enumerated(EnumType.STRING)
    private BannerType bannerType;

    @Enumerated
    private BaseStatus bannerStatus;
    //activated,



}