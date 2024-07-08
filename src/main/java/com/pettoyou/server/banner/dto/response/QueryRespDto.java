package com.pettoyou.server.banner.dto.response;

import com.pettoyou.server.banner.entity.Banner;
import com.pettoyou.server.photo.entity.PhotoData;
import lombok.Builder;

@Builder
public record QueryRespDto(
    Long bannerId,
    String bannerName,
    PhotoData bannerImg,
    String bannerLink
) {

    public static QueryRespDto from(Banner banner) {
        return new QueryRespDto(
                banner.getBannerId(),
                banner.getBannerName(),
                banner.getBannerImg(),
                banner.getBannerLink());

    }
}
