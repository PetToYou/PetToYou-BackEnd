package com.pettoyou.server.pet.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class PetMedicalInfo {
    private float weight;
    private Long registerNumber;
    private String neuteringStatus;
    private String basicVaccinationStatus;


}

//Weight float
//IsDesex boolean #neutering o, x
//RegisterNumber long
//NeuteringStatus string
//BasicVaccinationStatus string
