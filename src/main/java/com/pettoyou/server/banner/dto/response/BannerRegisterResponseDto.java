package com.pettoyou.server.banner.dto.response;


import lombok.Builder;

@Builder
public record BannerRegisterResponseDto(
        Long bannerId
) {
    public static BannerRegisterResponseDto from(Long bannerId) {
        return BannerRegisterResponseDto.builder()
                .bannerId(bannerId)
                .build();
    }
}
