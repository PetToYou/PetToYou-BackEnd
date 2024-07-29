package com.pettoyou.server.pet.dto.response;

import com.pettoyou.server.pet.entity.Pet;
import com.pettoyou.server.pet.entity.PetMedicalInfo;
import com.pettoyou.server.pet.entity.enums.PetType;
import com.pettoyou.server.pet.entity.enums.Species;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record PetDetailInfoRespDto(
        //Todo : 추후에 펫 상세 화면 나오면 수정해야함.
        Long petId,
        String petName,
        Species species,
        LocalDate birth,
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
                .birth(pet.getBirth())
                .petType(pet.getPetType())
                .adoptionDate(pet.getAdoptionDate())
                .petMedicalInfo(pet.getPetMedicalInfo())
                .profileImgUrl(pet.getProfilePhotoData() == null ? "test.url" : pet.getProfilePhotoData().getPhotoUrl())
                .build();
    }
}
