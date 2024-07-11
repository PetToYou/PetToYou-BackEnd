package com.pettoyou.server.pet.service;

import com.pettoyou.server.pet.dto.PetDto;
import com.pettoyou.server.pet.dto.request.PetRegisterReqDto;
import com.pettoyou.server.pet.dto.response.PetRegisterRespDto;
import com.pettoyou.server.pet.dto.response.PetSimpleInfoDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PetService {
    PetRegisterRespDto petRegister(
            List<MultipartFile> petProfileImgs,
            PetRegisterReqDto petRegisterDto,
            Long loginMemberId);

    void petModify(
            Long petId,
            List<MultipartFile> petProfileImgs,
            PetRegisterReqDto petRegisterDto,
            Long loginMemberId
    );

    void petDelete(Long petId);

    List<PetSimpleInfoDto> queryPetList(Long userId);

    PetDto.Response.PetDetailInfo fetchPetDetailInfo(Long petId);
}
