package com.pettoyou.server.hospital.controller;

import com.pettoyou.server.config.security.service.PrincipalDetails;
import com.pettoyou.server.constant.dto.ApiResponse;
import com.pettoyou.server.constant.enums.CustomResponseStatus;
import com.pettoyou.server.hospital.dto.HospitalListDto;
import com.pettoyou.server.hospital.entity.Hospital;
import com.pettoyou.server.hospital.service.HospitalService;
import com.pettoyou.server.hospital.dto.HospitalDto;

import com.pettoyou.server.pet.dto.PetDto;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/hospital")
@Slf4j
public class HospitalController {

    private final HospitalService hospitalService;


    //특정 반경 내에
    @GetMapping()
    public ResponseEntity<ApiResponse<Page<HospitalListDto.Response>>> getHospitalList(Pageable pageable, @RequestBody HospitalListDto.Request location) {

        Page<HospitalListDto.Response> hospitalList = hospitalService.getHospitals(pageable, location);

        return ResponseEntity.ok().body(ApiResponse.createSuccess(hospitalList, CustomResponseStatus.SUCCESS));

    }

    //특정 반경 + 영업 중
    @GetMapping("/main")
    public ResponseEntity<ApiResponse<Page<HospitalListDto.Response>>> getHospitalOpenList(Pageable pageable, @RequestBody HospitalListDto.Request location) {

        Page<HospitalListDto.Response> hospitalOpenList = hospitalService.getHospitalsOpen(pageable, location);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(hospitalOpenList, CustomResponseStatus.SUCCESS));
    }

    //병원 상세페이지
    @GetMapping("/{hospitalId}")
    public ResponseEntity<ApiResponse<HospitalDto.Response>> getHospital(@PathVariable Long hospitalId) {

        HospitalDto.Response hospital = hospitalService.getHospitalById(hospitalId);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(hospital, CustomResponseStatus.SUCCESS));
    }


    @PostMapping("/admin")
    public ResponseEntity<ApiResponse<String>> registerHospital(
            @RequestPart(required = false, value = "hospitalImg") List<MultipartFile> hospitalImg,
            @RequestPart(required = false, value = "storeInfoImg") MultipartFile storeInfoImg,
            @RequestPart(required = false, value = "thumbnailImg") MultipartFile thumbnailImg,
            @RequestPart(value = "hospitalDto") @Valid HospitalDto.Request hospitalDto
            ) {
        String hospitalId = hospitalService.registerHospital(hospitalImg, storeInfoImg,thumbnailImg, hospitalDto);

        // 현재 요청 URI에서 path 부분만 가져오기
        String currentPath = ServletUriComponentsBuilder.fromCurrentRequest().build().getPath();
// "/admin" 부분 제거하기
        String newPath = currentPath.replace("/admin", "");
// 새로운 URI 생성
        URI location = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(newPath + "/{id}")
                .buildAndExpand(hospitalId)
                .toUri();

        return ResponseEntity.created(location).body(ApiResponse.createSuccess("등록 완료", CustomResponseStatus.SUCCESS));
    }


}
