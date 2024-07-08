package com.pettoyou.server.hospital.service;

import com.pettoyou.server.constant.enums.CustomResponseStatus;
import com.pettoyou.server.constant.exception.CustomException;
import com.pettoyou.server.hospital.dto.HospitalDto;
import com.pettoyou.server.hospital.dto.HospitalListDto;
import com.pettoyou.server.hospital.dto.HospitalTagDto;
import com.pettoyou.server.hospital.entity.*;
import com.pettoyou.server.hospital.repository.HospitalRepository;
import com.pettoyou.server.photo.converter.PhotoConverter;
import com.pettoyou.server.photo.entity.PhotoData;
import com.pettoyou.server.store.entity.StorePhoto;
import com.pettoyou.server.store.entity.StorePhotoRepository;
import com.pettoyou.server.store.interfaces.StoreInterface;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class HospitalServiceImpl implements HospitalService {
    private final TagMapperRepository tagMapperRepository;
    private final HospitalTagRepository hospitalTagRepository;
    private final HospitalRepository hospitalRepository;
    private final StorePhotoRepository storePhotoRepository;
    private final PhotoConverter photoConverter;
    //    public HospitalServiceImpl(HospitalRepository hospitalRepository) {
    //        this.hospitalRepository = hospitalRepository;
    //    } - > RequiredArgsConstructor로 대체

    @Override
    public String registerHospital(List<MultipartFile> hospitalImgs, MultipartFile storeInfoImg, MultipartFile thumbnailImg, HospitalDto.Request hospitalDto) {

        Hospital hospital;
        PhotoData thumbnail;
        List<StorePhoto> storePhotoList = new ArrayList<>();

        //thumbnail 저장
        if((thumbnailImg!=null)&& (!thumbnailImg.isEmpty()) ){
            thumbnail = photoConverter.ImgUpload(thumbnailImg);
        }
        else {
            thumbnail = new PhotoData(null, null, null);
            //기본값 처리하기!!!
            //thumbnailUrl="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSHQQdAY4HvcdOtRxApXStj7oRvUNKlATHpWA&s";
        }

        //병원 저장
        if((storeInfoImg!=null)&&(!storeInfoImg.isEmpty())){
            PhotoData photoData = photoConverter.ImgUpload(storeInfoImg);
            hospital = HospitalDto.Request.toHospitalEntity(hospitalDto, thumbnail, photoData );
        }
        else {
            hospital = HospitalDto.Request.toHospitalEntity(hospitalDto, thumbnail);
        }

        //Store 사진 저장 - 메소드 분리
        if((hospitalImgs!=null)&&(!hospitalImgs.isEmpty())){
            storePhotoList = photoConverter.StoreImgsToEntity(hospitalImgs, hospital);
            hospital.getStorePhotos().addAll(storePhotoList);
            //병원 객체에 양방향 매핑
//
            // cascade.all 옵션
        }
        //Tag 저장
        List<HospitalTag> tags = hospitalTagRepository.findAllById(hospitalDto.getTagIdList());
        List<TagMapper> tagMapperList = HospitalTagDto.toEntity(hospital, tags);

        hospital.getTags().addAll(tagMapperList);
        //hospital 객체에 tagMapper 매핑

//
//cascade.all

        //반드시 Tag 저장 후, StorePhoto 저장 후.  병원 저장
        hospitalRepository.save(hospital);
        if(!storePhotoList.isEmpty()){
            storePhotoRepository.saveAll(storePhotoList);
        }
        //cascade.all 옵션으로 자동으로 날아가면 각가의 photo에 대해 모두 날아가버림. repository.save 옵션으로 저장되기 때문. saveAll을 해줘야 쿼리가 한번만 나간다.
        tagMapperRepository.saveAll(tagMapperList);
        return hospital.getStoreId().toString();
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
