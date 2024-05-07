package com.pettoyou.server.pet.controller;

import com.pettoyou.server.config.security.service.PrincipalDetails;
import com.pettoyou.server.constant.dto.ApiResponse;
import com.pettoyou.server.constant.enums.CustomResponseStatus;
import com.pettoyou.server.pet.dto.PetDto;
import com.pettoyou.server.pet.service.PetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/v1/member")
@RequiredArgsConstructor
public class PetController {
    private final PetService petService;
    @PostMapping("/pet")
    public ResponseEntity<ApiResponse<PetDto.Response.Register>> petRegister(
            @RequestPart(value = "petProfileImg") List<MultipartFile> petProfileImg,
            @RequestPart(required = false, value = "petRegisterDto") PetDto.Request.Register petRegisterDto,
            @AuthenticationPrincipal PrincipalDetails principalDetails) {
        log.info("petImg : {}", petProfileImg);
        log.info("petRegisterDto : {}", petRegisterDto);
        Long memberId = principalDetails.getUserId();
        PetDto.Response.Register register = petService.petRegister(petProfileImg, petRegisterDto, memberId);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(register, CustomResponseStatus.SUCCESS));
    }
}
