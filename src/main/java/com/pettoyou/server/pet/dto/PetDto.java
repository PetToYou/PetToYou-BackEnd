package com.pettoyou.server.pet.dto;

import lombok.Data;

public class PetDto {

    public static class Request {

        @Data
        public static class Register {

        }
    }

    public static class Response {

        @Data
        public static class Register {
            private String petName;
        }
    }
}
