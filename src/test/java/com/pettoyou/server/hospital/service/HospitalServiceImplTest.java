package com.pettoyou.server.hospital.service;

import com.pettoyou.server.hospital.dto.response.HospitalDetail;
import com.pettoyou.server.hospital.entity.Hospital;
import com.pettoyou.server.hospital.entity.HospitalTag;
import com.pettoyou.server.hospital.entity.enums.HospitalTagType;
import com.pettoyou.server.hospital.repository.HospitalRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
@SpringBootTest
class HospitalServiceImplTest {

    @Mock
    private HospitalRepository hospitalRepository;

    @Autowired
    private HospitalServiceImpl hospitalServiceImpl;

    @Test
    void getHospitalsTest() {
    }

    @Test
    void getHospitalSearch() {
    }

    @Test
    void getHospitalDetail() {
        Long hospitalId =1L;

        HospitalTag tag1 = HospitalTag.builder().tagType(HospitalTagType.SERVICE)
                .tagContent("병원")
                .hospitalTagId(1L)
                .build();
        HospitalTag tag2 = HospitalTag.builder().tagType(HospitalTagType.SERVICE)
                .tagContent("미용")
                .hospitalTagId(1L)
                .build();

        List<HospitalTag> tagList = Arrays.asList(tag1, tag2);

        Hospital hospital = Hospital.builder()
                .storeId(1L)
                .storeName("hospital")
                .additionalServiceTag("zero-pay")
                .build();


        when(hospitalRepository.findById(hospitalId)).thenReturn(Optional.of(hospital));
        when(hospitalRepository.findTagList(hospitalId)).thenReturn(tagList);

        HospitalDetail hospitalDetail = hospitalServiceImpl.getHospitalDetail(hospitalId);

        assertThat(hospitalDetail).isNotNull();
        assertThat(hospitalDetail.hospitalId()).isEqualTo(hospitalId);
        assertThat(hospitalDetail.hospitalTags().services().size()).isEqualTo(2);

    }

    @Test
    void registerHospital() {
    }
}