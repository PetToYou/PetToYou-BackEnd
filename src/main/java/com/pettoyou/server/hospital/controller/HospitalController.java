package com.pettoyou.server.hospital.controller;

import com.pettoyou.server.hospital.dto.HospitalListDto;
import com.pettoyou.server.hospital.entity.Hospital;
import com.pettoyou.server.hospital.service.HospitalService;
import com.pettoyou.server.hospital.dto.HospitalDto;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/hospital")
public class HospitalController {

    private final HospitalService hospitalService;



    @GetMapping("/contain")
    public List<HospitalListDto.Response> getHospitalList(@RequestBody HospitalListDto.Request location){

        List<HospitalListDto.Response> hospitalList = hospitalService.getHospitalsContain(location);


        return hospitalList;



    }
    @GetMapping("/{hospitalId}")
    public HospitalDto getHospital(@PathVariable Long hospitalId){

        HospitalDto hospital = hospitalService.getHospitalById(hospitalId);
        return hospital;
    }




}
