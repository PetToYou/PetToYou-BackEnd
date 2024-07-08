package com.pettoyou.server.hospital.service;

import com.pettoyou.server.constant.enums.CustomResponseStatus;
import com.pettoyou.server.constant.exception.CustomException;
import com.pettoyou.server.hospital.dto.request.HospitalQueryCond;
import com.pettoyou.server.hospital.dto.request.HospitalQueryInfo;
import com.pettoyou.server.hospital.dto.response.HospitalDetail;
import com.pettoyou.server.hospital.entity.Hospital;
import com.pettoyou.server.hospital.repository.HospitalRepository;
import com.pettoyou.server.store.dto.response.StoreQueryTotalInfo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class HospitalServiceImpl implements HospitalService {

    private final HospitalRepository hospitalRepository;

    // 병원 상세 조회
    @Override
    public HospitalDetail getHospitalDetail(Long hospitalId) {
        Hospital findHospital = hospitalRepository.findDistinctHospitalByStoreId(hospitalId)
                .orElseThrow(() -> new CustomException(CustomResponseStatus.HOSPITAL_NOT_FOUND));

        return HospitalDetail.toDto(findHospital);
    }

    // 특정 반경내의 병원 조회
    @Override
    public Page<StoreQueryTotalInfo> getHospitals(
            Pageable pageable,
            HospitalQueryInfo queryInfo,
            HospitalQueryCond queryCond
    ) {
        Page<StoreQueryTotalInfo> hospitalsWithinRadius = hospitalRepository.findHospitalsWithinRadius(
                pageable,
                getDayOfWeekNum(),
                queryInfo.toPointString(),
                LocalTime.now(),
                queryCond
        );

        hospitalRepository.findHospitalOptimization(
                getDayOfWeekNum(),
                queryInfo.toPointString(),
                LocalTime.now(),
                queryCond
        );

        return hospitalsWithinRadius;
    }

    // Get 요일 숫자 데이터 1~7
    private Integer getDayOfWeekNum() {
        return LocalDate.now().getDayOfWeek().getValue();
    }

}
