package com.pettoyou.server.pet.service;

import com.pettoyou.server.pet.dto.PetDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PetService {
    PetDto.Response.Register petRegister (
            List<MultipartFile> petProfileImgs,
            PetDto.Request.Register petRegisterDto,
            String loginUsername);
}
