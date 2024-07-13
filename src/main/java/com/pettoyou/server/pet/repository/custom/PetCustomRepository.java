package com.pettoyou.server.pet.repository.custom;

import com.pettoyou.server.pet.dto.response.PetSimpleInfoDto;
import com.pettoyou.server.pet.entity.Pet;

import java.util.List;
import java.util.Optional;

public interface PetCustomRepository {
    List<PetSimpleInfoDto> findAllPetsByMemberId(Long memberId);

    Optional<Pet> findPetUsingPetIdAndMemberId(Long petId, Long memberId);
}
