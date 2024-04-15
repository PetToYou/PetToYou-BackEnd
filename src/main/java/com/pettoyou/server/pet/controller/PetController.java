package com.pettoyou.server.pet.controller;

import com.pettoyou.server.constant.dto.ApiResponse;
import com.pettoyou.server.constant.enums.CustomResponseStatus;
import com.pettoyou.server.pet.dto.PetDto;
import com.pettoyou.server.pet.service.PetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PetController {
    private final PetService petService;
    @PostMapping("/pet")
    public ResponseEntity<ApiResponse<PetDto.Response.Register>> petRegister(
            @RequestPart(value = "petProfileImg") List<MultipartFile> petProfileImg,
            @RequestPart(required = false, value = "petRegisterDto") PetDto.Request.Register petRegisterDto,
            Authentication authentication) {
        log.info("petImg : {}", petProfileImg);
        log.info("petRegisterDto : {}", petRegisterDto);
        UserDetails loginUser = (UserDetails) authentication.getPrincipal();
        String loginUsername = loginUser.getUsername();
        PetDto.Response.Register register = petService.petRegister(petProfileImg, petRegisterDto, loginUsername);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(register, CustomResponseStatus.SUCCESS));
    }
}
