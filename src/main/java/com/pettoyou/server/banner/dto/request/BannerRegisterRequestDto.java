package com.pettoyou.server.banner.dto.request;

import com.pettoyou.server.banner.entity.enums.BannerType;
import lombok.Builder;

@Builder
public record BannerRegisterRequestDto(String bannerName, String bannerLink, BannerType bannerType) {}
