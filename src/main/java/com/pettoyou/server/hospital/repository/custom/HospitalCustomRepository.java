package com.pettoyou.server.hospital.repository.custom;

import com.pettoyou.server.hospital.dto.request.HospitalQueryCond;
import com.pettoyou.server.hospital.dto.request.HosptialSearchQueryInfo;
import com.pettoyou.server.hospital.dto.response.HospitalDetail;
import com.pettoyou.server.hospital.dto.response.HospitalDtoWithAddress;
import com.pettoyou.server.hospital.dto.response.HospitalDtoWithDistance;
import com.pettoyou.server.hospital.entity.HospitalTag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalTime;
import java.util.List;

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

    List<HospitalTag> findTagList(Long hospitalId);
}
