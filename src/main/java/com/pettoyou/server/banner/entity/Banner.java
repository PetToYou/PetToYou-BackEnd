package com.pettoyou.server.banner.entity;

import com.pettoyou.server.banner.dto.request.BannerRegisterRequestDto;
import com.pettoyou.server.banner.entity.enums.BannerType;
import com.pettoyou.server.constant.entity.BaseEntity;
import com.pettoyou.server.constant.enums.BaseStatus;
import com.pettoyou.server.photo.entity.PhotoData;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Getter
@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SQLDelete(sql = "UPDATE banner SET banner_status = 'DEACTIVATE' WHERE banner_id = ?")
@SQLRestriction("banner_status = 'ACTIVATE'")
@Table(name = "banner")
public class Banner extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bannerId;
    private String bannerName;
    private PhotoData bannerImg;
    private String bannerLink;

    @Enumerated(EnumType.STRING)
    private BannerType bannerType;

    @Enumerated(EnumType.STRING)
    private BaseStatus bannerStatus;

    private Banner(String bannerName, PhotoData bannerImg, String bannerLink, BannerType bannerType) {
        this.bannerName = bannerName;
        this.bannerImg = bannerImg;
        this.bannerLink = bannerLink;
        this.bannerType = bannerType;
        this.bannerStatus = BaseStatus.ACTIVATE;
    }

    public static Banner of(BannerRegisterRequestDto bannerDto, PhotoData bannerImg) {
        return new Banner(bannerDto.bannerName(), bannerImg, bannerDto.bannerLink(), bannerDto.bannerType());
    }

    public void bannerModify(BannerRegisterRequestDto bannerDto, PhotoData bannerImg) {
        this.bannerName = bannerDto.bannerName();
        this.bannerImg = bannerImg;
        this.bannerLink = bannerDto.bannerLink();
    }

    public String getImgBucket() {
        return bannerImg.getBucket();
    }

    public String getImgKey() {
        return bannerImg.getObject();
    }
}