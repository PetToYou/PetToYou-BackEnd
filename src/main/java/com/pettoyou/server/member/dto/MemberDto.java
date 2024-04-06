package com.pettoyou.server.member.dto;

import com.pettoyou.server.constant.entity.AuthTokens;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class MemberDto {
    public static class Request {


    }

    public static class Response {

        @Data
        @Builder
        @AllArgsConstructor
        @NoArgsConstructor
        public static class SignIn {
            private AuthTokens authTokens;
            private String nickname;

            public static SignIn of(AuthTokens authTokens, String nickname) {
                return SignIn.builder()
                        .authTokens(authTokens)
                        .nickname(nickname)
                        .build();
            }
        }

        @Data
        @Builder
        @AllArgsConstructor
        @NoArgsConstructor
        public static class Reissue {
            private AuthTokens authTokens;

            public static Reissue from(AuthTokens authTokens) {
                return Reissue.builder()
                        .authTokens(authTokens)
                        .build();
            }
        }
    }
}
