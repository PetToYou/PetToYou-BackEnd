package com.pettoyou.server.hospital.controller;

import com.pettoyou.server.constant.dto.ApiResponse;
import com.pettoyou.server.constant.enums.CustomResponseStatus;
import com.pettoyou.server.hospital.dto.HospitalListDto;
import com.pettoyou.server.hospital.service.HospitalService;
import com.pettoyou.server.hospital.dto.HospitalDto;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/hospital")
public class HospitalController {

    private final HospitalService hospitalService;



    //특정 반경 내에
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
    public ResponseEntity<ApiResponse<HospitalDto>> getHospital(@PathVariable Long hospitalId){

        HospitalDto hospital = hospitalService.getHospitalById(hospitalId);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(hospital, CustomResponseStatus.SUCCESS));
    }







}
