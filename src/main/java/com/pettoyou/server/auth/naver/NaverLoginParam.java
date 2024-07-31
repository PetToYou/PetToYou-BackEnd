package com.pettoyou.server.auth.naver;

import com.pettoyou.server.auth.OAuthLoginParams;
import com.pettoyou.server.member.entity.enums.OAuthProvider;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class NaverLoginParam implements OAuthLoginParams {
    private String authorizationCode;
    private String state;

    public static NaverLoginParam of(String authorizationCode, String state) {
        return new NaverLoginParam(authorizationCode, state);
    }

    @Override
    public OAuthProvider oAuthProvider() {
        return OAuthProvider.NAVER;
    }

    @Override
    public MultiValueMap<String, String> makeBody() {
        MultiValueMap<String, String > body = new LinkedMultiValueMap<>();
        body.add("code", authorizationCode);
        body.add("state", state);
        return body;
    }
}
