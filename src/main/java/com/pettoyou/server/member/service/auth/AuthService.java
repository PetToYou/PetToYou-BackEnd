package com.pettoyou.server.member.service.auth;

import com.pettoyou.server.auth.OAuthLoginParams;
import com.pettoyou.server.constant.entity.AuthTokens;
import com.pettoyou.server.member.dto.MemberDto;

public interface AuthService {
    AuthTokens signIn(OAuthLoginParams param);
    MemberDto.Response.Reissue reissue(String refreshToken);
    void logout(String accessToken);
}
