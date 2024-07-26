package com.pettoyou.server.hospital.service;

import com.pettoyou.server.constant.enums.CustomResponseStatus;
import com.pettoyou.server.constant.exception.CustomException;
import com.pettoyou.server.hospital.dto.response.HospitalDetail;
import com.pettoyou.server.hospital.entity.Hospital;
import com.pettoyou.server.hospital.entity.HospitalTag;
import com.pettoyou.server.hospital.entity.enums.HospitalTagType;
import com.pettoyou.server.hospital.repository.HospitalRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class HospitalServiceImplTest {

    @Mock
    private HospitalRepository hospitalRepository;

    @InjectMocks
    private HospitalServiceImpl hospitalServiceImpl;

    private static final Logger logger = LoggerFactory.getLogger(HospitalServiceImplTest.class);

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

    @Test
    void getHospitalsTest() {
    }

    @Test
    void getHospitalSearch() {
    }

    @Test
    void getHospitalDetail() {


        when(hospitalRepository.findById(hospitalId)).thenReturn(Optional.ofNullable(hospital));
        when(hospitalRepository.findTagList(hospitalId)).thenReturn(tagList);

        HospitalDetail hospitalDetail = hospitalServiceImpl.getHospitalDetail(hospitalId);

        assertThat(hospitalDetail).isNotNull();
        assertThat(hospitalDetail.hospitalId()).isEqualTo(hospitalId);
        assertThat(hospitalDetail.hospitalTags().services().size()).isEqualTo(2);

    }

    @Test
    void getHospitalDetail_태그없는경우(){
        when(hospitalRepository.findById(hospitalId)).thenReturn(Optional.ofNullable(hospital));
        when(hospitalRepository.findTagList(hospitalId)).thenReturn(null);
        HospitalDetail hospitalDetail = hospitalServiceImpl.getHospitalDetail(hospitalId);

        assertThat(hospitalDetail).isNotNull();
        assertThat(hospitalDetail.hospitalId()).isEqualTo(hospitalId);
        assertThat(hospitalDetail.hospitalTags().services()).isEqualTo(null);
        assertThat(hospitalDetail.hospitalTags().specialities()).isEqualTo(null);
        assertThat(hospitalDetail.hospitalTags().businessHours()).isEqualTo(null);
        assertThat(hospitalDetail.hospitalTags().emergency()).isEqualTo(null);
    }

    @Test
    void getHospitalDetail_병원없는경우(){
        when(hospitalRepository.findById(hospitalId)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> hospitalServiceImpl.getHospitalDetail(hospitalId))
                .isInstanceOf(CustomException.class)
                .hasMessageContaining(CustomResponseStatus.STORE_NOT_FOUND.getMessage());
    }

    @Test
    void registerHospital() {
    }
}