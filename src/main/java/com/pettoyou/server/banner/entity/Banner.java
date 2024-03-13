package com.pettoyou.server.banner.entity;

import com.pettoyou.server.banner.entity.enums.BannerType;
import com.pettoyou.server.constant.entity.BaseEntity;
import com.pettoyou.server.constant.enums.BaseStatus;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "banner")
public class Banner extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bannerId;
    private String bannerName;
    private String bannerImg;
    private String bannerLink;

    @Enumerated(EnumType.STRING)
    private BannerType bannerType;

    @Enumerated(EnumType.STRING)
    private BaseStatus bannerStatus;
    //activated,



}