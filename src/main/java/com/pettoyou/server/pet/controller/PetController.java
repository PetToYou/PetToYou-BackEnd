package com.pettoyou.server.pet.controller;

import com.pettoyou.server.config.security.service.PrincipalDetails;
import com.pettoyou.server.constant.dto.ApiResponse;
import com.pettoyou.server.constant.enums.CustomResponseStatus;
import com.pettoyou.server.pet.dto.PetDto;
import com.pettoyou.server.pet.dto.request.PetRegisterReqDto;
import com.pettoyou.server.pet.dto.response.PetRegisterRespDto;
import com.pettoyou.server.pet.dto.response.PetSimpleInfoDto;
import com.pettoyou.server.pet.service.PetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/v1/member")
@RequiredArgsConstructor
public class PetController {
    private final PetService petService;

    @PostMapping("/pet")
    public ResponseEntity<ApiResponse<PetRegisterRespDto>> petRegister(
            @RequestPart(required = false, value = "petProfileImg") List<MultipartFile> petProfileImg,
            @RequestPart(value = "petRegisterDto") PetRegisterReqDto petRegisterDto,
            @AuthenticationPrincipal PrincipalDetails principalDetails) {

        PetRegisterRespDto response = petService.petRegister(petProfileImg, petRegisterDto, principalDetails.getUserId());
        return ResponseEntity.ok().body(ApiResponse.createSuccess(response, CustomResponseStatus.SUCCESS));
    }

    @PutMapping("/pet/{id}")
    public ResponseEntity<ApiResponse<String>> petModify(
            @PathVariable Long id,
            @RequestPart(required = false, value = "petProfileImg") List<MultipartFile> petProfileImg,
            @RequestPart(value = "petModifyDto") PetRegisterReqDto petRegisterDto,
            @AuthenticationPrincipal PrincipalDetails principalDetails) {

        petService.petModify(id, petProfileImg, petRegisterDto, principalDetails.getUserId());
        return ResponseEntity.ok().body(ApiResponse.createSuccess("수정완료!", CustomResponseStatus.SUCCESS));
    }

    @DeleteMapping("/pet/{id}")
    public ResponseEntity<ApiResponse<String>> petDelete(
            @PathVariable Long id) {
        petService.petDelete(id);
        return ResponseEntity.ok().body(ApiResponse.createSuccess("삭제완료!", CustomResponseStatus.SUCCESS));
    }

    @GetMapping("/pets")
    public ResponseEntity<ApiResponse<List<PetSimpleInfoDto>>> petsQuery(
            @AuthenticationPrincipal PrincipalDetails principalDetails
    ) {
        List<PetSimpleInfoDto> response = petService.queryPetList(principalDetails.getUserId());

        return ResponseEntity.ok().body(ApiResponse.createSuccess(response, CustomResponseStatus.SUCCESS));
    }

    @GetMapping("/pet/{id}")
    public ResponseEntity<ApiResponse<PetDto.Response.PetDetailInfo>> fetchPetDetailInfo(
            @PathVariable Long id) {

        PetDto.Response.PetDetailInfo response = petService.fetchPetDetailInfo(id);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(response, CustomResponseStatus.SUCCESS));
    }
}
