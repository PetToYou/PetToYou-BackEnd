package com.pettoyou.server.hospital.service;

import com.pettoyou.server.hospital.dto.HospitalListDto;
import com.pettoyou.server.hospital.entity.Hospital;
import com.pettoyou.server.hospital.interfaces.ContainInterface;
import com.pettoyou.server.hospital.dto.HospitalDto;
import com.pettoyou.server.hospital.repository.HospitalRepository;
import com.pettoyou.server.store.dto.BusinessHourDto;
import com.pettoyou.server.store.dto.StorePhotoDto;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
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
    public HospitalDto getHospitalById(Long hospitalId) {


        Hospital hospital = hospitalRepository.findDistinctHospitalFetchJoinByStoreId(hospitalId).orElseThrow(
                () -> new EntityNotFoundException("No Hospital Found"));
        //Repository에서 Optional로 반환 -> orElsThrow로 exception 처리 가능. 값을 꺼냈으므로 Optional이 아닌 hospital 객체에 담아준다.

        HospitalDto hospitalDto = toHospitalDto(hospital);



        return hospitalDto;


    }

    private HospitalDto toHospitalDto(Hospital hospital) {

        return HospitalDto.builder()
                .hospitalId(hospital.getStoreId())
                .hospitalName(hospital.getStoreName())
                .storePhone(hospital.getStorePhone())
                .notice(hospital.getNotice())
                .websiteLink(hospital.getWebsiteLink())
                .additionalServiceTag(hospital.getAdditionalServiceTag())
                .storeInfo(hospital.getStoreInfo())
                .storeInfoPhoto(hospital.getStoreInfoPhoto())
                .address(hospital.getAddress())
                .businessHours(hospital.getBusinessHours().stream()
                        .map(BusinessHourDto::toDto)
                        .collect(Collectors.toList()))
                .storePhotos(hospital.getStorePhotos().stream()
                        .map(StorePhotoDto::toDto)
                        .collect(Collectors.toList()))
                .registrationInfo(hospital.getRegistrationInfo())
                .build();

    }

    //위치기반 근처 병원 조회 (일반)
    @Override
    public List<HospitalListDto.Response> getHospitalsContain(HospitalListDto.Request location) {

        String point = location.toPointString();
        Integer radius = location.getRadius();
        Integer dayOfWeek = getDayOfWeekNum();

        List<ContainInterface> hospitals = hospitalRepository.findHospitalsContain(point, radius, dayOfWeek);
//        List<ContainInterface> hospitals = hospitalRepository.findHospitalsContain(point, radius, dayOfWeek);
        log.info(hospitals.toString());


        List<HospitalListDto.Response> result = hospitals.stream()
                .filter(h -> h.getHospitalName() != null || h.getBusinessHours() != null)
                .map(h ->

                        HospitalListDto.Response.builder()
                                .storeId(h.getStoreId())
                                .hospitalName(h.getHospitalName())
                                .thumbnailUrl(h.getThumbnailUrl())
                                .distance(String.format("%.1f", h.getDistance() / 1000.0))
                                .openHour(Optional.ofNullable(h.getBusinessHours()).map(bh -> bh.getStartTime()).map(Object::toString).orElse("영업 시간 정보 없음"))
                                .closeHour(Optional.ofNullable(h.getBusinessHours()).map(bh -> bh.getEndTime()).map(Object::toString).orElse("영업 시간 정보 없음"))
                                .breakTime(Optional.ofNullable(h.getBusinessHours()).map(bh -> bh.getBreakEndTime())
                                        .map(Object::toString)
                                        .orElse("No Break Time"))
                                .reviewCount(h.getReviewCount())
                                .rateAvg(h.getRateAvg())
                                .build())
                .collect(Collectors.toList());
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
