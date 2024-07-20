package com.pettoyou.server.member.dto.response;

import com.pettoyou.server.constant.entity.AuthTokens;
import lombok.Builder;

@Builder
public record LoginRespDto(
    String accessToken,
    Long exprTime
) {
    public static LoginRespDto from(AuthTokens authTokens) {
        return LoginRespDto.builder()
                .accessToken(authTokens.accessToken())
                .exprTime(authTokens.exprTime())
                .build();
    }
}
