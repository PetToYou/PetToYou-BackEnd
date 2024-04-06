package com.pettoyou.server.pet.controller;

import com.pettoyou.server.constant.dto.ApiResponse;
import com.pettoyou.server.constant.enums.CustomResponseStatus;
import com.pettoyou.server.pet.dto.PetDto;
import com.pettoyou.server.pet.service.PetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PetController {
    private final PetService petService;
    @PostMapping("/pet")
    public ResponseEntity<ApiResponse<PetDto.Response.Register>> petRegister(PetDto.Request.Register petRegisterDto) {

        return ResponseEntity.ok().body(ApiResponse.createSuccess(null, CustomResponseStatus.SUCCESS));
    }
}
