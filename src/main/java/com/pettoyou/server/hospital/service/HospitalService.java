package com.pettoyou.server.hospital.service;

import com.pettoyou.server.hospital.dto.HospitalListDto;
import com.pettoyou.server.hospital.dto.request.HospitalQueryInfo;
import com.pettoyou.server.hospital.dto.response.HospitalDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HospitalService {
  HospitalDetail getHospitalDetail(Long hospitalId);

  Page<HospitalListDto.Response> getHospitals(
          Pageable pageable,
          HospitalQueryInfo queryInfo
  );

  Page<HospitalListDto.Response> getHospitalsOpen(
          Pageable pageable,
    HospitalListDto.Request location
  );
}
