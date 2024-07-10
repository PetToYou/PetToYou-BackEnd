package com.pettoyou.server.hospital.service;

import com.pettoyou.server.hospital.dto.HospitalDto;
import com.pettoyou.server.hospital.dto.request.HospitalQueryCond;
import com.pettoyou.server.hospital.dto.request.HospitalQueryInfo;
import com.pettoyou.server.hospital.dto.response.HospitalDetail;
import com.pettoyou.server.hospital.dto.response.TestDTO;
import com.pettoyou.server.store.dto.response.StoreQueryTotalInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface HospitalService {

    String registerHospital(
            List<MultipartFile> hospitalImg,
            MultipartFile storeInfoImg,
            MultipartFile thumbnailImg,
            HospitalDto.Request hospital);

    Page<StoreQueryTotalInfo> getHospitals(
            Pageable pageable,
            HospitalQueryInfo queryInfo,
            HospitalQueryCond queryCond
    );

    Page<TestDTO> getHospitalsTest(
            Pageable pageable,
            HospitalQueryInfo queryInfo,
            HospitalQueryCond queryCond
    );

    HospitalDetail getHospitalDetail(
            Long hospitalId
    );


}
