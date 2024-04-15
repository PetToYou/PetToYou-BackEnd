package com.pettoyou.server.pet.entity;

import com.pettoyou.server.constant.enums.CustomResponseStatus;
import com.pettoyou.server.constant.exception.CustomException;
import com.pettoyou.server.pet.entity.enums.Bmi;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Embeddable
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PetMedicalInfo {
    private Double weight;

    @Enumerated(EnumType.STRING)
    private Bmi bmi;

    private Long registerNumber;

    private String neuteringStatus;

    private String basicVaccinationStatus;

    private String allergy;

    private String caution;

    public static PetMedicalInfo toPetMedicalInfo(PetMedicalInfo petMedicalInfo) {
        return builder()
                .weight(petMedicalInfo.getWeight() != null ? petMedicalInfo.getWeight() : null)
                .bmi(petMedicalInfo.getBmi() != null ? petMedicalInfo.getBmi() : null)
                .registerNumber(petMedicalInfo.getRegisterNumber() != null ? petMedicalInfo.getRegisterNumber() : null)
                .neuteringStatus(petMedicalInfo.getNeuteringStatus() != null ? petMedicalInfo.getNeuteringStatus() : null)
                .basicVaccinationStatus(petMedicalInfo.getBasicVaccinationStatus() != null ? petMedicalInfo.getBasicVaccinationStatus() : null)
                .allergy(petMedicalInfo.getAllergy() != null ? petMedicalInfo.getAllergy() : null)
                .caution(petMedicalInfo.getCaution() != null ? petMedicalInfo.getCaution() : null)
                .build();
    }

    private Bmi bmiType(String bmi) {
        if (bmi.equals("THIN")) return Bmi.THIN;
        else if (bmi.equals("NORMAL")) return Bmi.NORMAL;
        else return Bmi.OBESE;
    }
}

//Weight float
//IsDesex boolean #neutering o, x
//RegisterNumber long
//NeuteringStatus string
//BasicVaccinationStatus string
