package com.pettoyou.server.auth;

import com.pettoyou.server.member.entity.enums.OAuthProvider;

public interface OAuthInfoResponse {

    String getId();

    String getEmail();

    String getNickname();

    String getPhone();

    String getName();

    OAuthProvider getOAuthProvider();
}
