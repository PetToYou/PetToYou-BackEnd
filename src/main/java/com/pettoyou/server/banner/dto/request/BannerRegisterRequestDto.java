package com.pettoyou.server.banner.dto.request;

import lombok.Builder;

@Builder
public record BannerRegisterRequestDto(String bannerName, String bannerLink) {}
