package com.pettoyou.server.hospital.service;

import com.pettoyou.server.hospital.dto.HospitalDto;
import com.pettoyou.server.hospital.dto.HospitalListDto;
import com.pettoyou.server.hospital.entity.Hospital;
import com.pettoyou.server.store.interfaces.StoreInterface;
import com.pettoyou.server.hospital.repository.HospitalRepository;
import com.pettoyou.server.store.dto.BusinessHourDto;
import com.pettoyou.server.store.dto.StorePhotoDto;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
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
  public HospitalDto getHospitalById(Long hospitalId) {
    Hospital hospital = hospitalRepository
      .findDistinctHospitalByStoreId(hospitalId)
      .orElseThrow(() -> new EntityNotFoundException("No Hospital Found"));
    //Repository에서 Optional로 반환 -> orElseThrow로 exception 처리 가능. 값을 꺼냈으므로 Optional이 아닌 hospital 객체에 담아준다.
    HospitalDto hospitalDto = HospitalDto.toHospitalDto(hospital);
    return hospitalDto;
  }

  //DTO 로 옮기자
//  private HospitalDto toHospitalDto(Hospital hospital) {
//    return HospitalDto
//      .builder()
//      .hospitalId(hospital.getStoreId())
//      .hospitalName(hospital.getStoreName())
//      .storePhone(hospital.getStorePhone())
//      .notice(hospital.getNotice())
//      .websiteLink(hospital.getWebsiteLink())
//      .additionalServiceTag(hospital.getAdditionalServiceTag())
//      .storeInfo(hospital.getStoreInfo())
//      .storeStatus(hospital.getStoreStatus())
//      .storeInfoPhoto(hospital.getStoreInfoPhoto())
//      .address(hospital.getAddress())
//      .businessHours(
//        hospital
//          .getBusinessHours()
//          .stream()
//          .map(BusinessHourDto::toDto)
//          .collect(Collectors.toList())
//      )
//      .storePhotos(
//        hospital
//          .getStorePhotos()
//          .stream()
//          .map(StorePhotoDto::toDto)
//          .collect(Collectors.toList())
//      )
//      .registrationInfo(hospital.getRegistrationInfo())
//      .build();
//  }

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
//    LocalDate date = LocalDate.now();
//    DayOfWeek dayOfWeek = date.getDayOfWeek();
//    return dayOfWeek.getValue();
    return LocalDate.now().getDayOfWeek().getValue();
  }
  //
  //필요한 정보. 현재 status,

}
