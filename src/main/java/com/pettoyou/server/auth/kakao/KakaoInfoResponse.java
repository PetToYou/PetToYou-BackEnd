package com.pettoyou.server.auth.kakao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pettoyou.server.auth.OAuthInfoResponse;
import com.pettoyou.server.member.entity.enums.OAuthProvider;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoInfoResponse implements OAuthInfoResponse {

    @JsonProperty("kakao_account")
    private KakaoAccount kakaoAccount;

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class KakaoAccount {
        private KakaoProfile profile;
        private String email;
        private String name;
        private String phone_number;
    }

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class KakaoProfile {
        // 예시로 닉네임이지. 추가로 프사 url 정보도 여기서 받아오면 됨
        private String nickname;
    }
    @Override
    public String getEmail() {
        return kakaoAccount.getEmail();
    }

    @Override
    public String getNickname() {
        return kakaoAccount.profile.getNickname();
    }

    @Override
    public String getPhone() {
        return kakaoAccount.getPhone_number();
    }

    @Override
    public String getName() {
        return kakaoAccount.getName();
    }

    @Override
    public OAuthProvider getOAuthProvider() {
        return OAuthProvider.KAKAO;
    }
}
