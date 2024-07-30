package com.pettoyou.server.pet.dto.response;

import com.pettoyou.server.pet.entity.Pet;
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
                .profileImgUrl(pet.getProfileImgUrl())
                .petName(pet.getPetName())
                .species(pet.getSpecies())
                .gender(pet.getGenderLabel())
                .age(pet.petAgeCalculate(LocalDate.now()))
                .weight(pet.getPetWeight())
                .build();
    }

}
