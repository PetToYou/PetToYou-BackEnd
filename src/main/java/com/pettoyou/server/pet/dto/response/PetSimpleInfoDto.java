package com.pettoyou.server.pet.dto.response;

import com.pettoyou.server.pet.entity.Pet;
import com.pettoyou.server.pet.entity.enums.Gender;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record PetSimpleInfoDto(
        Long petId,
        String profileImgUrl,
        String petName,
        String species,
        String gender,
        Integer age,
        Double weight
) {
    public static PetSimpleInfoDto of(Pet pet) {
        return PetSimpleInfoDto.builder()
                .petId(pet.getPetId())
                .profileImgUrl(pet.getPetProfilePhotos().get(0).getPhotoData().getPhotoUrl())
                .petName(pet.getPetName())
                .species(pet.getSpecies())
                .gender(pet.getGender() == Gender.MALE ? "남아" : "여아")
                .age(pet.petAgeCalculate(LocalDate.now()))
                .weight(pet.getPetMedicalInfo().getWeight()) // Todo : 5.0으로 나오나? 테스트해봐야함
                .build();
    }
}
