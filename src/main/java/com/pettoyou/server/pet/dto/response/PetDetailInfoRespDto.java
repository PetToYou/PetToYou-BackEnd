package com.pettoyou.server.pet.dto.response;

import com.pettoyou.server.pet.entity.Pet;
import com.pettoyou.server.pet.entity.PetMedicalInfo;
import com.pettoyou.server.pet.entity.enums.PetType;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record PetDetailInfoRespDto(
        //Todo : 추후에 펫 상세 화면 나오면 수정해야함.
        Long petId,
        String petName,
        String species,
        PetType petType,
        LocalDate adoptionDate,
        PetMedicalInfo petMedicalInfo,
        String profileImgUrl
) {
    public static PetDetailInfoRespDto from(Pet pet) {
        return PetDetailInfoRespDto.builder()
                .petId(pet.getPetId())
                .petName(pet.getPetName())
                .species(pet.getSpecies())
                .petType(pet.getPetType())
                .adoptionDate(pet.getAdoptionDate())
                .petMedicalInfo(pet.getPetMedicalInfo())
                .profileImgUrl(pet.getPetProfilePhotos().get(0).getPhotoData().getPhotoUrl())
                .build();
    }
}
