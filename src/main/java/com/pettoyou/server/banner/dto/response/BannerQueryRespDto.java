package com.pettoyou.server.banner.dto.response;

import com.pettoyou.server.banner.entity.Banner;
import com.pettoyou.server.photo.entity.PhotoData;
import lombok.Builder;

@Builder
public record BannerQueryRespDto(
    Long bannerId,
    String bannerName,
    PhotoData bannerImg,
    String bannerLink
) {

    public static BannerQueryRespDto from(Banner banner) {
        return new BannerQueryRespDto(
                banner.getBannerId(),
                banner.getBannerName(),
                banner.getBannerImg(),
                banner.getBannerLink());

    }
}
