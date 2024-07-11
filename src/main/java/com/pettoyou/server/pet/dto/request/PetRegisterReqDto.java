package com.pettoyou.server.pet.dto.request;

import com.pettoyou.server.pet.entity.enums.*;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record PetRegisterReqDto(
        @NotBlank(message = "반려동물의 타입(강아지 or 고양이)을 선택해주세요.")
        PetType petType,
        @NotBlank(message = "반려동물의 이름을 입력해주세요.")
        String petName,
        @NotBlank(message = "반려동물의 생일을 입력해주세요.")
        LocalDate birth,
        LocalDate adoptionDate,
        @NotBlank(message = "반려동물의 성별을 입력해주세요.")
        Gender gender,
        @NotBlank(message = "반려동물의 품종을 입력해주세요.")
        String species,
        PetMedicalInfoDto petMedicalInfoDto
) {
}
