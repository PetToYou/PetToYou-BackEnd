package com.pettoyou.server.pet.service;

import com.pettoyou.server.pet.dto.request.PetRegisterAndModifyReqDto;
import com.pettoyou.server.pet.dto.response.PetDetailInfoRespDto;
import com.pettoyou.server.pet.dto.response.PetRegisterRespDto;
import com.pettoyou.server.pet.dto.response.PetSimpleInfoDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PetService {
    PetRegisterRespDto petRegister(
            MultipartFile petProfileImgs,
            PetRegisterAndModifyReqDto petRegisterDto,
            Long loginMemberId);

    void petModify(
            Long petId,
            MultipartFile petProfileImg,
            PetRegisterAndModifyReqDto petRegisterDto,
            Long loginMemberId
    );

    void petDelete(
            Long petId,
            Long loginMemberId
    );

    List<PetSimpleInfoDto> queryPetList(
            Long userId
    );

    PetDetailInfoRespDto fetchPetDetailInfo(
            Long petId,
            Long loginMemberId
    );
}
