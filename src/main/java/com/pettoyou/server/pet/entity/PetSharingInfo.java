package com.pettoyou.server.pet.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class PetSharingInfo {
    private String allergy;
    private String caution;

}