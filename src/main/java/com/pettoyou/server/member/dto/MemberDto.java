package com.pettoyou.server.member.dto;

import com.pettoyou.server.constant.entity.AuthTokens;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class MemberDto {
    public static class Request {

        @Data
        @Builder
        @AllArgsConstructor
        @NoArgsConstructor
        public static class Login {

        }


    }

    public static class Response {

        @Data
        @Builder
        @AllArgsConstructor
        @NoArgsConstructor
        public static class SignIn {
            private AuthTokens authTokens;
            private String nickname;
        }
    }
}
