package com.pettoyou.server.pet.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PetSharingInfo {
    private String allergy;
    private String caution;
}