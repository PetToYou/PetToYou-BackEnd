package com.pettoyou.server.hospital.repository.custom;

import com.pettoyou.server.hospital.dto.request.HospitalQueryCond;
import com.pettoyou.server.store.dto.response.StoreQueryTotalInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalTime;

public interface HospitalCustomRepository {
    Page<StoreQueryTotalInfo> findHospitalsWithinRadius(
            Pageable pageable,
            int dayOfWeek,
            String point,
            LocalTime now,
            HospitalQueryCond queryCond
    );
}
