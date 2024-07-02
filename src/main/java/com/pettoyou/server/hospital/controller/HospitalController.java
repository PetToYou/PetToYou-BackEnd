package com.pettoyou.server.hospital.controller;

import com.pettoyou.server.constant.dto.ApiResponse;
import com.pettoyou.server.constant.enums.CustomResponseStatus;
import com.pettoyou.server.hospital.dto.HospitalListDto;
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

    //특정 반경 내의 병원 조회 -> 모든 병원들을 조회함
    @GetMapping()
    public ResponseEntity<ApiResponse<Page<HospitalListDto.Response>>> getHospitalList(Pageable pageable, @RequestBody HospitalListDto.Request location){

        Page<HospitalListDto.Response> hospitalList = hospitalService.getHospitals(pageable, location);

        return ResponseEntity.ok().body(ApiResponse.createSuccess(hospitalList, CustomResponseStatus.SUCCESS));

    }

    //특정 반경 + 영업 중
    @GetMapping("/main")
    public ResponseEntity<ApiResponse<Page<HospitalListDto.Response>>> getHospitalOpenList(Pageable pageable, @RequestBody HospitalListDto.Request location){

        Page<HospitalListDto.Response> hospitalOpenList =hospitalService.getHospitalsOpen(pageable, location);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(hospitalOpenList, CustomResponseStatus.SUCCESS));
    }

    //병원 상세페이지
    @GetMapping("/{hospitalId}")
    public ResponseEntity<ApiResponse<HospitalDetail>> getHospitalDetail(@PathVariable Long hospitalId){
        HospitalDetail response = hospitalService.getHospitalDetail(hospitalId);

        return ResponseEntity.ok().body(ApiResponse.createSuccess(response, CustomResponseStatus.SUCCESS));
    }

    // 병원 조회 필터링



}
