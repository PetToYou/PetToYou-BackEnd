package com.pettoyou.server.hospital.service;

import com.pettoyou.server.constant.enums.BaseStatus;
import com.pettoyou.server.constant.exception.CustomException;
import com.pettoyou.server.store.dto.AddressDto;
import com.pettoyou.server.hospital.dto.HospitalDto;
import com.pettoyou.server.hospital.dto.HospitalListDto;
import com.pettoyou.server.hospital.entity.Hospital;
import com.pettoyou.server.store.dto.RegistrationInfoDto;
import com.pettoyou.server.store.entity.Address;
import com.pettoyou.server.store.entity.BusinessHour;
import com.pettoyou.server.store.entity.RegistrationInfo;
import com.pettoyou.server.store.entity.Store;
import com.pettoyou.server.store.entity.enums.StoreType;
import com.pettoyou.server.store.interfaces.StoreInterface;
import com.pettoyou.server.hospital.repository.HospitalRepository;
import com.pettoyou.server.store.dto.BusinessHourDto;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class HospitalServiceImpl implements HospitalService {

    private final HospitalRepository hospitalRepository;

    //    public HospitalServiceImpl(HospitalRepository hospitalRepository) {
    //        this.hospitalRepository = hospitalRepository;
    //    } - > RequiredArgsConstructor로 대체

    @Override
    public String registerHospital(HospitalDto.Request hospitalDto) {

        Hospital hospital = toHospitalEntity(hospitalDto);
        hospitalRepository.save(hospital);


        return hospital.getStoreId().toString();
    }

    public static Hospital toHospitalEntity(HospitalDto.Request hospitalDtoRequest) {

        if (hospitalDtoRequest == null) {
            throw new IllegalArgumentException("HospitalDto.Request is null");
        }

        Hospital hospital = Hospital.builder()
                .additionalServiceTag(hospitalDtoRequest.getAdditionalServiceTag())
                .storeName(hospitalDtoRequest.getHospitalName())
                .storePhone(hospitalDtoRequest.getHospitalPhone())
                .notice(hospitalDtoRequest.getNotice())
                .websiteLink(hospitalDtoRequest.getWebsiteLink())
                .storeInfo(hospitalDtoRequest.getStoreInfo())
                .thumbnailUrl(hospitalDtoRequest.getThumbnailUrl())
                .storeInfoPhoto(hospitalDtoRequest.getStoreInfoPhoto())
                .businessHours(new ArrayList<>())
                .registrationInfo(RegistrationInfoDto.Request.toEntity(hospitalDtoRequest.getRegistrationInfo(), StoreType.HOSPITAL))
                .address(AddressDto.toEntity(hospitalDtoRequest.getAddress()))
                .storeStatus(BaseStatus.ACTIVATE)
                .build();


        //연관관계 설정.
         hospital.getBusinessHours()
                 .addAll(hospitalDtoRequest.getBusinessHours()
                         .stream()
                         .map(businessHours->BusinessHourDto.Request.toEntity(businessHours,hospital))//BusinessHour에 hospital에 담아줌
                         .collect(Collectors.toList()));
    //Registration Info 추가



        return hospital;
    }


    @Override
    public HospitalDto.Response getHospitalById(Long hospitalId) {
        Hospital hospital = hospitalRepository
                .findDistinctHospitalByStoreId(hospitalId)
                .orElseThrow(() -> new EntityNotFoundException("No Hospital Found"));

        if(hospital.getRegistrationInfo()==null){
            throw new IllegalArgumentException("RegistrationInfo is null. 폐업된 가게입니다. ");
        }

        HospitalDto.Response hospitalDto = HospitalDto.Response.toHospitalDto(hospital);
        return hospitalDto;
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
        //        List<Interface> hospitals = hospitalRepository.findHospitals(point, radius, dayOfWeek);

        List<HospitalListDto.Response> hospitalList = HospitalListDto.Response.toListDto(hospitals.getContent());


        return new PageImpl<>(hospitalList, pageable, hospitals.getTotalElements());

    }

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
        //        List<Interface> hospitals = hospitalRepository.findHospitals(point, radius, dayOfWeek);

        List<HospitalListDto.Response> result = HospitalListDto.Response.toListDto(hospitals.getContent());

        return new PageImpl<>(result, pageable, hospitals.getTotalElements());
    }

    // Get 요일 숫자 데이터 1~7
    private Integer getDayOfWeekNum() {
        return LocalDate.now().getDayOfWeek().getValue();
    }


}
