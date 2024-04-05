package com.pettoyou.server.pet.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PetMedicalInfo {
    private double weight;

    private Long registerNumber;

    private String neuteringStatus;

    private String basicVaccinationStatus;

    private String allergy;

    private String caution;
}

//Weight float
//IsDesex boolean #neutering o, x
//RegisterNumber long
//NeuteringStatus string
//BasicVaccinationStatus string
