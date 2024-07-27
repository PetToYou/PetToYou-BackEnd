package com.pettoyou.server.hospital.service;

import com.pettoyou.server.constant.enums.CustomResponseStatus;
import com.pettoyou.server.constant.exception.CustomException;
import com.pettoyou.server.hospital.dto.HospitalTagDto;
import com.pettoyou.server.hospital.dto.request.HospitalDto;
import com.pettoyou.server.hospital.dto.request.HospitalQueryAddressInfo;
import com.pettoyou.server.hospital.dto.request.HospitalQueryCond;
import com.pettoyou.server.hospital.dto.request.HosptialSearchQueryInfo;
import com.pettoyou.server.hospital.dto.response.HospitalDetail;
import com.pettoyou.server.hospital.dto.response.HospitalDtoWithAddress;
import com.pettoyou.server.hospital.dto.response.HospitalDtoWithDistance;
import com.pettoyou.server.hospital.entity.Hospital;
import com.pettoyou.server.hospital.entity.HospitalTag;
import com.pettoyou.server.hospital.entity.TagMapper;
import com.pettoyou.server.hospital.repository.HospitalRepository;
import com.pettoyou.server.hospital.repository.HospitalTagRepository;
import com.pettoyou.server.hospital.repository.TagMapperRepository;
import com.pettoyou.server.photo.entity.PhotoData;
import com.pettoyou.server.photo.service.PhotoService;
import com.pettoyou.server.store.entity.StorePhoto;
import com.pettoyou.server.store.repository.StorePhotoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalTime;
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
    private final PhotoService photoService;

    @Override
    public Page<HospitalDtoWithDistance> getHospitalsTest(Pageable pageable, HospitalQueryAddressInfo queryInfo, HospitalQueryCond queryCond) {
        return hospitalRepository.findHospitalOptimization(
                pageable,
                getDayOfWeekNum(),
                queryInfo.toPointString(),
                LocalTime.now(),
                queryCond
        );
    }

    @Override
    public Page<HospitalDtoWithAddress> getHospitalSearch(Pageable pageable, HosptialSearchQueryInfo queryInfo){
        return hospitalRepository.findHospitalBySearch(pageable, queryInfo, getDayOfWeekNum());
    }

    // 병원 상세 조회
    @Override
    public HospitalDetail getHospitalDetail(Long hospitalId) {
//        return hospitalRepository.findHospitalDetailById(hospitalId);
        Hospital hospital = hospitalRepository.findById(hospitalId)
                .orElseThrow(()-> new CustomException(CustomResponseStatus.STORE_NOT_FOUND));

        List<HospitalTag> tagList = hospitalRepository.findTagList(hospitalId);

        return HospitalDetail.from(hospital, tagList);

    }

    @Override
    public String registerHospital(List<MultipartFile> hospitalImg, MultipartFile storeInfoImg, MultipartFile thumbnailImg, HospitalDto hospitalDto) {
        PhotoData thumbnail = photoService.handleThumbnail(thumbnailImg);

        Hospital hospital = handleStoreInfoImg(storeInfoImg, hospitalDto, thumbnail);
        List<StorePhoto> storePhotoList = photoService.handleHospitalImgs(hospitalImg, hospital);
        hospital.getStorePhotos().addAll(storePhotoList);

        List<TagMapper> tagMapperList = handleTags(hospital, hospitalDto);

        hospitalRepository.save(hospital);
        if (!storePhotoList.isEmpty()) {
            hospital.getStorePhotos().addAll(storePhotoList);
            storePhotoRepository.saveAll(storePhotoList);
        }
        tagMapperRepository.saveAll(tagMapperList);
        return hospital.getStoreId().toString();
    }

    private Hospital handleStoreInfoImg(MultipartFile storeInfoImg, HospitalDto hospitalDto, PhotoData thumbnail) {
        if ((storeInfoImg != null) && (!storeInfoImg.isEmpty())) {
            PhotoData photoData = photoService.uploadImage(storeInfoImg);
            return HospitalDto.toHospitalEntity(hospitalDto, thumbnail, photoData);
        } else {
            return HospitalDto.toHospitalEntity(hospitalDto, thumbnail);
        }
    }

    private List<TagMapper> handleTags(Hospital hospital, HospitalDto hospitalDto) {
        List<HospitalTag> tags = hospitalTagRepository.findAllById(hospitalDto.tagIdList());
        return HospitalTagDto.toEntity(hospital, tags);
    }


    // Get 요일 숫자 데이터 1~7
    private Integer getDayOfWeekNum() {
        return LocalDate.now().getDayOfWeek().getValue();
    }

}
