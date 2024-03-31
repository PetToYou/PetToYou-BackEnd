package com.pettoyou.server.hospital.service;

import com.pettoyou.server.hospital.interfaces.ContainInterface;
import com.pettoyou.server.hospital.dto.HospitalDto;
import com.pettoyou.server.hospital.entity.Hospital;
import com.pettoyou.server.hospital.repository.HospitalRepository;
import com.pettoyou.server.store.entity.BusinessHour;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class HospitalServiceImpl implements HospitalService {

    private final HospitalRepository hospitalRepository;

    public HospitalServiceImpl(HospitalRepository hospitalRepository) {
        this.hospitalRepository = hospitalRepository;
    }

    @Override
    public List<Hospital> getAllHospList() {

        List<Hospital> allHospitals = hospitalRepository.findAll();
        return allHospitals;
    }

    //위치기반 근처 병원 조회 (일반)
    @Override
    public List<HospitalDto.Test> getHospitalsContain(HospitalDto.Request location) {

        String point = location.toPointString();
        Integer radius = location.getRadius();

        Integer dayOfWeek = getDayOfWeekNum();

        List<ContainInterface> hospitals = hospitalRepository.findHospitalsContain(point, radius, dayOfWeek);
//        List<ContainInterface> hospitals = hospitalRepository.findHospitalsContain(point, radius, dayOfWeek);
        log.info(hospitals.toString());

//        businessHour 가공하기
//        거리 meter -> km 변환 소수점 한자리까지


//        System.out.println(Arrays.toString(hospitals.toArray()));



        List<HospitalDto.Test> result = hospitals.stream()
                .filter(h -> h.getHospitalName() != null || h.getBusinessHours() != null)
                .map(h ->

                        HospitalDto.Test.builder()
                                .storeId(h.getStoreId())
                                .hospitalName(h.getHospitalName())
                                .thumbnailUrl(h.getThumbnailUrl())
                                .distance(String.format("%.1f", h.getDistance()/1000.0))
                                .openHour(Optional.ofNullable(h.getBusinessHours()).map(bh -> bh.getStartTime()).map(Object::toString).orElse("영업 시간 정보 없음"))
                                .closeHour(Optional.ofNullable(h.getBusinessHours()).map(bh-> bh.getEndTime()).map(Object::toString).orElse("영업 시간 정보 없음"))
                                .breakTime(Optional.ofNullable(h.getBusinessHours()).map(bh->bh.getBreakEndTime())
                                        .map(Object::toString)
                                        .orElse("No Break Time"))
                                .build())
                .collect(Collectors.toList());
//        List<HospitalDto.Test> result = hospitals.stream().map(h -> new HospitalDto.Test(h.getStoreId(), h.getHospitalName(), h.getThumbnailUrl(), h.getDistance(),h.getBusinessHour())).collect(Collectors.toList());
        return result;

    }


    // Get 요일 숫자 데이터 1~7
    private Integer getDayOfWeekNum() {
        LocalDate date = LocalDate.now();
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return dayOfWeek.getValue();
    }

    //필요한 정보. 현재 status,


}
