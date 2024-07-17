package com.pettoyou.server.hospital.service;

import com.pettoyou.server.hospital.dto.HospitalDto;
import com.pettoyou.server.hospital.dto.request.HospitalQueryCond;
import com.pettoyou.server.hospital.dto.request.HospitalQueryAddressInfo;
import com.pettoyou.server.hospital.dto.response.HospitalDetail;
import com.pettoyou.server.hospital.dto.response.TestDTO;
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

    Page<TestDTO> getHospitalsTest(
            Pageable pageable,
            HospitalQueryAddressInfo queryInfo,
            HospitalQueryCond queryCond
    );

    HospitalDetail getHospitalDetail(
            Long hospitalId
    );


}
