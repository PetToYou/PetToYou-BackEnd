package com.pettoyou.server.hospital.service;


import com.pettoyou.server.hospital.dto.HospitalDto;
import com.pettoyou.server.hospital.entity.Hospital;

import java.util.List;

public interface HospitalService {
//    HospListDto.Response getHospList(HospListDto.Request location);

     List<Hospital> getAllHospList();
     //테스트 용

     List<HospitalDto.Test> getHospitalsContain(HospitalDto.Request location);
}
