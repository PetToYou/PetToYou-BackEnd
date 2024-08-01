package com.pettoyou.server.hospital.controller;

import com.pettoyou.server.constant.dto.ApiResponse;
import com.pettoyou.server.hospital.dto.request.HospitalQueryAddressInfo;
import com.pettoyou.server.hospital.dto.request.HospitalQueryCond;
import com.pettoyou.server.hospital.dto.request.HosptialSearchQueryInfo;
import com.pettoyou.server.hospital.dto.response.HospitalDetail;
import com.pettoyou.server.hospital.dto.response.HospitalDtoWithAddress;
import com.pettoyou.server.hospital.dto.response.HospitalDtoWithDistance;
import com.pettoyou.server.hospital.service.HospitalService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Hospital", description = "Hospital 관련 API 입니다.")
@RequiredArgsConstructor
@RequestMapping("/api/v1/hospital")
@Slf4j
public class HospitalController {

    private final HospitalService hospitalService;

    // 모든 병원 조회 + 필터링 가능
    @GetMapping()
    public ResponseEntity<ApiResponse<Page<HospitalDtoWithDistance>>> getHospitalList(
//            @PageableDefault(size = 20, sort = "created_at", direction = Sort.Direction.DESC)
            Pageable pageable,
            @ModelAttribute HospitalQueryAddressInfo queryInfo,
            @ModelAttribute HospitalQueryCond queryCond
    ){
        log.info("queryInfo : {}", queryInfo);
        log.info("queryCond : {}", queryCond);



        Page<HospitalDtoWithDistance> response = hospitalService.getHospitalsTest(pageable, queryInfo, queryCond);
        return ApiResponse.createSuccessWithOk(response);
    }
    //병원 검색 조회
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<Page<HospitalDtoWithAddress>>> getSearchHospitalList(Pageable pageable, @Valid @ModelAttribute HosptialSearchQueryInfo queryInfo){
        Page<HospitalDtoWithAddress> response = hospitalService.getHospitalSearch(pageable, queryInfo);
        return ApiResponse.createSuccessWithOk(response);
    }

    // 병원 상세페이지 조회
    @GetMapping("/{hospitalId}")
    public ResponseEntity<ApiResponse<HospitalDetail>> getHospitalDetail(@PathVariable Long hospitalId){
        HospitalDetail response = hospitalService.getHospitalDetail(hospitalId);
        return ApiResponse.createSuccessWithOk(response);
    }
}
