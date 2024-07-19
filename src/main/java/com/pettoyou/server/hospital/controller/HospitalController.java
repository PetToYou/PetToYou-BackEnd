package com.pettoyou.server.hospital.controller;

import com.pettoyou.server.constant.dto.ApiResponse;
import com.pettoyou.server.constant.enums.CustomResponseStatus;
import com.pettoyou.server.hospital.dto.HospitalDto;
import com.pettoyou.server.hospital.dto.request.HospitalQueryCond;
import com.pettoyou.server.hospital.dto.request.HospitalQueryAddressInfo;
import com.pettoyou.server.hospital.dto.response.HospitalDetail;
import com.pettoyou.server.hospital.dto.response.TestDTO;
import com.pettoyou.server.hospital.service.HospitalService;

import com.pettoyou.server.store.dto.response.StoreQueryTotalInfo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
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

    // 모든 병원 조회 + 필터링 가능
    @GetMapping()
    public ResponseEntity<ApiResponse<Page<TestDTO>>> getHospitalList(
            Pageable pageable,
            @ModelAttribute HospitalQueryAddressInfo queryInfo,
            @ModelAttribute HospitalQueryCond queryCond
    ){
        log.info("queryInfo : {}", queryInfo);
        log.info("queryCond : {}", queryCond);

        Page<TestDTO> response = hospitalService.getHospitalsTest(pageable, queryInfo, queryCond);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(response, CustomResponseStatus.SUCCESS));
    }

    // 병원 상세페이지 조회
    @GetMapping("/{hospitalId}")
    public ResponseEntity<ApiResponse<HospitalDetail>> getHospitalDetail(@PathVariable Long hospitalId){
        HospitalDetail response = hospitalService.getHospitalDetail(hospitalId);

        return ResponseEntity.ok().body(ApiResponse.createSuccess(response, CustomResponseStatus.SUCCESS));
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
