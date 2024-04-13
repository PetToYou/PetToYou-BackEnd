package com.pettoyou.server.hospital.service;


import com.pettoyou.server.hospital.dto.HospitalListDto;
import com.pettoyou.server.hospital.dto.HospitalDto;
import com.pettoyou.server.hospital.entity.Hospital;

import java.util.List;

public interface HospitalService {
//    HospListDto.Response getHospList(HospListDto.Request location);

     //테스트 용


     HospitalDto getHospitalById(Long hosptialId);

     List<HospitalListDto.Response> getHospitalsContain(HospitalListDto.Request location);
}
