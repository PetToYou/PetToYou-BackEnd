package com.pettoyou.server.hospital.service;

import com.pettoyou.server.hospital.dto.request.HospitalQueryCond;
import com.pettoyou.server.hospital.dto.request.HospitalQueryInfo;
import com.pettoyou.server.hospital.dto.response.HospitalDetail;
import com.pettoyou.server.store.dto.response.StoreQueryTotalInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HospitalService {
  Page<StoreQueryTotalInfo> getHospitals(
          Pageable pageable,
          HospitalQueryInfo queryInfo,
          HospitalQueryCond queryCond
  );

  HospitalDetail getHospitalDetail(
          Long hospitalId
  );
}
