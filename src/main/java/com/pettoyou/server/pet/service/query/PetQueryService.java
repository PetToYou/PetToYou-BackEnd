package com.pettoyou.server.pet.service.query;

import com.pettoyou.server.pet.dto.response.PetDetailInfoRespDto;
import com.pettoyou.server.pet.dto.response.PetSimpleInfoDto;

import java.util.List;

public interface PetQueryService {
    List<PetSimpleInfoDto> queryPetList(
            Long userId
    );

    PetDetailInfoRespDto fetchPetDetailInfo(
            Long petId,
            Long loginMemberId
    );
}
