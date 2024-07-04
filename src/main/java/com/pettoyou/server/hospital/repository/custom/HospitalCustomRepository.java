package com.pettoyou.server.hospital.repository.custom;

import com.pettoyou.server.store.dto.response.StoreQueryInfo;
import com.pettoyou.server.store.dto.response.StoreQueryTotalInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalTime;

public interface HospitalCustomRepository {
    Page<StoreQueryTotalInfo> findHospitalsWithinRadius(Pageable pageable, int dayOfWeek, String point, int radius);
    Page<StoreQueryInfo> findHospitalsWithinRadiusAndOpen(Pageable pageable, int dayOfWeek, String point, int radius, LocalTime now);
}
