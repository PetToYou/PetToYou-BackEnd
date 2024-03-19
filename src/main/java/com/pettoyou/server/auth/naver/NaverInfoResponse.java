package com.pettoyou.server.auth.naver;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pettoyou.server.auth.OAuthInfoResponse;
import com.pettoyou.server.member.entity.enums.OAuthProvider;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class NaverInfoResponse implements OAuthInfoResponse {
    @JsonProperty("response")
    private Response response;

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class Response {
        private String email;
        private String nickname;
        private String name;
        private String mobile;
    }
    @Override
    public String getEmail() {
        return response.getEmail();
    }

    @Override
    public String getNickname() {
        return response.getNickname();
    }

    @Override
    public String getPhone() {
        return response.mobile;
    }

    @Override
    public String getName() {
        return response.name;
    }

    @Override
    public OAuthProvider getOAuthProvider() {
        return OAuthProvider.NAVER;
    }
}
