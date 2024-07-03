package com.pettoyou.server.hospital.controller;

import com.pettoyou.server.constant.dto.ApiResponse;
import com.pettoyou.server.constant.enums.CustomResponseStatus;
import com.pettoyou.server.store.dto.response.StoreQueryInfo;
import com.pettoyou.server.hospital.dto.request.HospitalQueryInfo;
import com.pettoyou.server.hospital.dto.response.HospitalDetail;
import com.pettoyou.server.hospital.service.HospitalService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/hospital")
public class HospitalController {

    private final HospitalService hospitalService;

    // 특정 반경 내의 모든 병원 조회
    @GetMapping()
    public ResponseEntity<ApiResponse<Page<StoreQueryInfo>>> getHospitalList(Pageable pageable, @ModelAttribute HospitalQueryInfo queryInfo){
        Page<StoreQueryInfo> response = hospitalService.getHospitals(pageable, queryInfo);

        return ResponseEntity.ok().body(ApiResponse.createSuccess(response, CustomResponseStatus.SUCCESS));
    }

    //특정 반경 + 영업 중 인 병원 조회
    @GetMapping("/main")
    public ResponseEntity<ApiResponse<Page<StoreQueryInfo>>> getHospitalOpenList(Pageable pageable, @ModelAttribute HospitalQueryInfo queryInfo){
        Page<StoreQueryInfo> result =hospitalService.getHospitalsOpen(pageable, queryInfo);

        return ResponseEntity.ok().body(ApiResponse.createSuccess(result, CustomResponseStatus.SUCCESS));
    }

    // 병원 상세페이지 조회
    @GetMapping("/{hospitalId}")
    public ResponseEntity<ApiResponse<HospitalDetail>> getHospitalDetail(@PathVariable Long hospitalId){
        HospitalDetail response = hospitalService.getHospitalDetail(hospitalId);

        return ResponseEntity.ok().body(ApiResponse.createSuccess(response, CustomResponseStatus.SUCCESS));
    }

    // 병원 조회 필터링



}
