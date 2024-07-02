package com.pettoyou.server.hospital.service;

import com.pettoyou.server.constant.enums.CustomResponseStatus;
import com.pettoyou.server.constant.exception.CustomException;
import com.pettoyou.server.hospital.dto.HospitalListDto;
import com.pettoyou.server.hospital.dto.response.HospitalDetail;
import com.pettoyou.server.hospital.entity.Hospital;
import com.pettoyou.server.hospital.repository.HospitalRepository;
import com.pettoyou.server.store.interfaces.StoreInterface;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

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
        //Repository에서 Optional로 반환 -> orElseThrow로 exception 처리 가능. 값을 꺼냈으므로 Optional이 아닌 hospital 객체에 담아준다.

        return HospitalDetail.toDto(findHospital);
    }

    //위치기반 근처 병원 조회 (일반)
    @Override
    public Page<HospitalListDto.Response> getHospitals(
            Pageable pageable,
            HospitalListDto.Request location
    ) {

        Page<StoreInterface> hospitals = hospitalRepository.findHospitals(
                pageable,
                location.toPointString(),
                location.getRadius(),
                getDayOfWeekNum()
        );

        List<HospitalListDto.Response> hospitalList = HospitalListDto.Response.toListDto(hospitals.getContent());

        return new PageImpl<>(hospitalList, pageable, hospitals.getTotalElements());
    }

    // 특정 반경 + 영업중인 병원 조회
    public Page<HospitalListDto.Response> getHospitalsOpen(
            Pageable pageable,
            HospitalListDto.Request location
    ) {

        Page<StoreInterface> hospitals = hospitalRepository.findHospitalsOpen(
                pageable,
                location.toPointString(),
                location.getRadius(),
                getDayOfWeekNum(),
                LocalTime.now()
        );

        List<HospitalListDto.Response> result = HospitalListDto.Response.toListDto(hospitals.getContent());

        return new PageImpl<>(result, pageable, hospitals.getTotalElements());
    }

    // Get 요일 숫자 데이터 1~7
    private Integer getDayOfWeekNum() {
        return LocalDate.now().getDayOfWeek().getValue();
    }

}
