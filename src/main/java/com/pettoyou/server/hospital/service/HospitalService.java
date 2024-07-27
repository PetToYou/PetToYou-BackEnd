package com.pettoyou.server.hospital.service;

import com.pettoyou.server.hospital.dto.request.HospitalDto;
import com.pettoyou.server.hospital.dto.request.HospitalQueryCond;
import com.pettoyou.server.hospital.dto.request.HospitalQueryAddressInfo;
import com.pettoyou.server.hospital.dto.request.HosptialSearchQueryInfo;
import com.pettoyou.server.hospital.dto.response.HospitalDetail;
import com.pettoyou.server.hospital.dto.response.HospitalDtoWithAddress;
import com.pettoyou.server.hospital.dto.response.HospitalDtoWithDistance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface HospitalService {

    String registerHospital(
            List<MultipartFile> hospitalImg,
            MultipartFile storeInfoImg,
            MultipartFile thumbnailImg,
            HospitalDto hospital);

    Page<HospitalDtoWithDistance> getHospitalsTest(
            Pageable pageable,
            HospitalQueryAddressInfo queryInfo,
            HospitalQueryCond queryCond
    );

    // 병원 검색. 거리 대신 주소로.
    Page<HospitalDtoWithAddress> getHospitalSearch(Pageable pageable, HosptialSearchQueryInfo queryInfo);

    HospitalDetail getHospitalDetail(
            Long hospitalId
    );


}
