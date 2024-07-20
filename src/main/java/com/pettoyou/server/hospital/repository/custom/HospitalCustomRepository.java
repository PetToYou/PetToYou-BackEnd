package com.pettoyou.server.hospital.repository.custom;

import com.pettoyou.server.hospital.dto.request.HospitalQueryCond;
import com.pettoyou.server.hospital.dto.request.HosptialSearchQueryInfo;
import com.pettoyou.server.hospital.dto.response.HospitalDetail;
import com.pettoyou.server.hospital.dto.response.HospitalDtoWithAddress;
import com.pettoyou.server.hospital.dto.response.HospitalDtoWithDistance;
import com.pettoyou.server.hospital.repository.HospitalRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalTime;

public interface HospitalCustomRepository {
    Page<HospitalDtoWithDistance> findHospitalOptimization(
            Pageable pageable,
            int dayOfWeek,
            String point,
            LocalTime now,
            HospitalQueryCond queryCond
    );

    Page<HospitalDtoWithAddress> findHospitalBySearch(Pageable pageable, HosptialSearchQueryInfo queryInfo, Integer dayOfWeek);

    HospitalDetail findHospitalDetailById(Long hospitalId);
}
