package com.pettoyou.server.pet.dto.request;

import com.pettoyou.server.pet.entity.enums.Bmi;
import com.pettoyou.server.pet.entity.enums.NeuteringStatus;
import com.pettoyou.server.pet.entity.enums.VaccinationStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record PetMedicalInfoDto(
        Double weight,
        Bmi bmi,
        String registerNumber,
        @NotBlank(message = "반려동물의 중성화 여부를 입력해주세요.")
        NeuteringStatus neuteringStatus,
        VaccinationStatus vaccinationStatus,
        String allergy,
        String currentFeedName,
        String medicalHistory
) {
}
