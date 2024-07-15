package com.pettoyou.server.member.dto.request;

public record LoginReqDto(
        String providerId,
        String name,
        String nickname,
        String email,
        String phone
) {
}
