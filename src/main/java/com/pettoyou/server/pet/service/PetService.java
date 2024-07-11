package com.pettoyou.server.pet.service;

import com.pettoyou.server.pet.dto.PetDto;
import com.pettoyou.server.pet.dto.response.PetSimpleInfoDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PetService {
    PetDto.Response.Register petRegister(
            List<MultipartFile> petProfileImgs,
            PetDto.Request.Register petRegisterDto,
            Long loginMemberId);

    void petModify(
            Long petId,
            List<MultipartFile> petProfileImgs,
            PetDto.Request.Register petRegisterDto,
            Long loginMemberId
    );

    void petDelete(Long petId);

    List<PetSimpleInfoDto> queryPetList(Long userId);

    PetDto.Response.PetDetailInfo fetchPetDetailInfo(Long petId);
}
