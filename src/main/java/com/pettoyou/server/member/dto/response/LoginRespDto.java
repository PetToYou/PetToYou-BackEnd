package com.pettoyou.server.member.dto.response;

import com.pettoyou.server.constant.entity.AuthTokens;

public record LoginRespDto(
    AuthTokens authTokens
) {
    public static LoginRespDto from(AuthTokens authTokens) {
        return new LoginRespDto(authTokens);
    }
}
