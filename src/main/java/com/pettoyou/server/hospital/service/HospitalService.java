package com.pettoyou.server.hospital.service;

import com.pettoyou.server.hospital.dto.HospitalDto;
import com.pettoyou.server.hospital.dto.HospitalListDto;
import com.pettoyou.server.hospital.entity.Hospital;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface HospitalService {
  //    HospListDto.Response getHospList(HospListDto.Request location);

  //테스트 용

  HospitalDto getHospitalById(Long hospitalId);

  Page<HospitalListDto.Response> getHospitals(
          Pageable pageable,
    HospitalListDto.Request location
  );

  Page<HospitalListDto.Response> getHospitalsOpen(
          Pageable pageable,
    HospitalListDto.Request location
  );
}
