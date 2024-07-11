package com.pettoyou.server.pet.repository.custom;

import com.pettoyou.server.pet.dto.response.PetSimpleInfoDto;

import java.util.List;

public interface PetCustomRepository {
    List<PetSimpleInfoDto> findAllPetsByMemberId(Long memberId);
}
