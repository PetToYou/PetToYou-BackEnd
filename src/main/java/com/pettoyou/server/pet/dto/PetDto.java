package com.pettoyou.server.pet.dto;

import com.pettoyou.server.pet.entity.PetMedicalInfo;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

public class PetDto {

    public static class Request {

        @Data
        public static class Register {
            // 반려동물 타입
            @NotBlank(message = "반려동물의 타입을 선택해주세요.")
            private String petType;
            // 이름
            @NotBlank(message = "반려동물의 이름을 입력해주세요.")
            private String name;
            // 생년월일
            @NotBlank(message = "반려동물의 생일을 입력해주세요.")
            private LocalDate birth;
            // 입양일
            private LocalDate adoptionDate;
            // 성별
            @NotBlank(message = "반려동물의 성별을 입력해주세요.")
            private String gender;
            // 품종
            @NotBlank(message = "반려동물의 품종을 입력해주세요.")
            private String species;

            private PetMedicalInfo petMedicalInfo;
        }
    }

    public static class Response {

        @Data
        @Builder
        public static class Register {
            private String petName;

            public static Register toDto(String petName) {
                return Register.builder().petName(petName).build();
            }
        }
    }
}
