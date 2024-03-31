package com.pettoyou.server.hospital.controller;

import com.pettoyou.server.hospital.dto.HospitalDto;
import com.pettoyou.server.hospital.entity.Hospital;
import com.pettoyou.server.hospital.service.HospitalService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/hospital")
public class HospitalController {

    private final HospitalService hospitalService;

    public HospitalController(HospitalService hospitalService){
        this.hospitalService =  hospitalService;
    }

    @GetMapping("/all")
    public List<Hospital> getHospitals(){

        List<Hospital> hospitalListALl = hospitalService.getAllHospList();

        return hospitalListALl;
    }// jpa test 용


    @GetMapping("/contain")
    public List<HospitalDto.Test> getHospitalList(@RequestBody HospitalDto.Request location){

        List<HospitalDto.Test> hospitalList = hospitalService.getHospitalsContain(location);


        return hospitalList;



    }


}
