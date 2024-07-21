package com.pettoyou.server.member.service.auth;

import com.pettoyou.server.auth.OAuthLoginParams;
import com.pettoyou.server.constant.entity.AuthTokens;

public interface AuthService {
    AuthTokens signIn(OAuthLoginParams param);
    AuthTokens reissue(String refreshToken);
    void logout(String accessToken);
}
