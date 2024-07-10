package com.pettoyou.server.hospital.controller;

import com.pettoyou.server.constant.dto.ApiResponse;
import com.pettoyou.server.constant.enums.CustomResponseStatus;
import com.pettoyou.server.hospital.dto.request.HospitalQueryCond;
import com.pettoyou.server.hospital.dto.request.HospitalQueryInfo;
import com.pettoyou.server.hospital.dto.response.HospitalDetail;
import com.pettoyou.server.hospital.dto.response.TestDTO;
import com.pettoyou.server.hospital.service.HospitalService;

import com.pettoyou.server.store.dto.response.StoreQueryTotalInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/hospital")
public class HospitalController {

    private final HospitalService hospitalService;

    // 모든 병원 조회 + 필터링 가능
    @GetMapping()
    public ResponseEntity<ApiResponse<Page<StoreQueryTotalInfo>>> getHospitalList(
            Pageable pageable,
            @ModelAttribute HospitalQueryInfo queryInfo,
            @ModelAttribute HospitalQueryCond queryCond
    ){
        log.info("queryInfo : {}", queryInfo);
        log.info("queryCond : {}", queryCond);

        Page<StoreQueryTotalInfo> response = hospitalService.getHospitals(pageable, queryInfo, queryCond);

        return ResponseEntity.ok().body(ApiResponse.createSuccess(response, CustomResponseStatus.SUCCESS));
    }

    @GetMapping("/test")
    public ResponseEntity<ApiResponse<Page<TestDTO>>> getHospitalListTest(
            Pageable pageable,
            @ModelAttribute HospitalQueryInfo queryInfo,
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

}
