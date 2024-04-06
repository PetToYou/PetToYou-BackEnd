package com.pettoyou.server.member.service;

import com.pettoyou.server.auth.OAuthLoginParams;
import com.pettoyou.server.member.dto.MemberDto;

public interface AuthService {
    MemberDto.Response.SignIn signIn(OAuthLoginParams param);
    MemberDto.Response.Reissue reissue(String refreshToken);
    void logout(String accessToken);
}
