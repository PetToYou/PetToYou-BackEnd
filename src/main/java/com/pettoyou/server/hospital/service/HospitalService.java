package com.pettoyou.server.hospital.service;

import com.pettoyou.server.store.dto.response.StoreQueryInfo;
import com.pettoyou.server.hospital.dto.request.HospitalQueryInfo;
import com.pettoyou.server.hospital.dto.response.HospitalDetail;
import com.pettoyou.server.store.dto.response.StoreQueryTotalInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HospitalService {
  HospitalDetail getHospitalDetail(
          Long hospitalId
  );

  Page<StoreQueryTotalInfo> getHospitals(
          Pageable pageable,
          HospitalQueryInfo queryInfo
  );

  Page<StoreQueryInfo> getHospitalsOpen(
          Pageable pageable,
          HospitalQueryInfo queryInfo
  );
}
