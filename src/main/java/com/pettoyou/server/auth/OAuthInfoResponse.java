package com.pettoyou.server.auth;

import com.pettoyou.server.member.entity.enums.OAuthProvider;

public interface OAuthInfoResponse {
    // 여기서 우리 서비스에 맞게 가져오면 될듯합니다.
    String getEmail();

    String getNickname();

    String getPhone();

    String getName();

    OAuthProvider getOAuthProvider();
}
