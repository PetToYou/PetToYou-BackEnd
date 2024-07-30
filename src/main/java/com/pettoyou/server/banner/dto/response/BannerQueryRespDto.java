package com.pettoyou.server.banner.dto.response;

import com.pettoyou.server.banner.entity.Banner;
import lombok.Builder;

@Builder
public record BannerQueryRespDto(
    Long bannerId,
    String bannerName,
    String bannerImgUrl,
    String bannerLink
) {
    public static BannerQueryRespDto from(Banner banner) {
        return new BannerQueryRespDto(
                banner.getBannerId(),
                banner.getBannerName(),
                banner.getBannerImg() == null ? "empty" : banner.getImgUrl(),
                banner.getBannerLink());
    }
}
