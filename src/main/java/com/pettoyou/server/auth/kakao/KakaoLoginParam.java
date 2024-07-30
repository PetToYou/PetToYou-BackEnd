package com.pettoyou.server.auth.kakao;

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
public class KakaoLoginParam implements OAuthLoginParams {
    private String authorizationCode;

    public static KakaoLoginParam from(String code) {
        return new KakaoLoginParam(code);
    }

    @Override
    public OAuthProvider oAuthProvider() {
        return OAuthProvider.KAKAO;
    }

    @Override
    public MultiValueMap<String, String> makeBody() {
        MultiValueMap<String, String > body = new LinkedMultiValueMap<>();
        body.add("code", authorizationCode);
        return body;
    }
}
