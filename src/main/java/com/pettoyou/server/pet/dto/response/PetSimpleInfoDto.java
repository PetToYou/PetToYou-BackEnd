package com.pettoyou.server.pet.dto.response;

import com.pettoyou.server.pet.entity.Pet;
import com.pettoyou.server.pet.entity.enums.Gender;
import com.pettoyou.server.pet.entity.enums.Species;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record PetSimpleInfoDto(
        Long petId,
        String profileImgUrl,
        String petName,
        Species species,
        String gender,
        String age,
        String weight
) {
    public static PetSimpleInfoDto of(Pet pet) {
        return PetSimpleInfoDto.builder()
                .petId(pet.getPetId())
                .profileImgUrl(pet.getProfilePhotoData().getPhotoUrl())
                .petName(pet.getPetName())
                .species(pet.getSpecies())
                .gender(pet.getGender() == Gender.MALE ? "남아" : "여아")
                .age(pet.petAgeCalculate(LocalDate.now()))
                .weight(formatWeight(pet.getPetMedicalInfo().getWeight()))
                .build();
    }

    private static String formatWeight(Double weight) {
        if (weight == null) {
            return null;
        }

        if (weight % 1 != 0) return String.valueOf(weight);
        return String.valueOf(weight.intValue());

    }
}
