package com.pettoyou.server.hospital.service;

import com.pettoyou.server.constant.enums.CustomResponseStatus;
import com.pettoyou.server.constant.exception.CustomException;
import com.pettoyou.server.hospital.dto.response.HospitalDetail;
import com.pettoyou.server.hospital.entity.Hospital;
import com.pettoyou.server.hospital.entity.HospitalTag;
import com.pettoyou.server.hospital.entity.enums.HospitalTagType;
import com.pettoyou.server.hospital.repository.HospitalRepository;
import com.pettoyou.server.photo.entity.PhotoData;
import com.pettoyou.server.store.entity.Address;
import com.pettoyou.server.store.entity.BusinessHour;
import com.pettoyou.server.store.entity.RegistrationInfo;
import com.pettoyou.server.store.entity.enums.StoreType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Time;
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

    HospitalTag tag1 = HospitalTag.builder()
            .tagType(HospitalTagType.SERVICE)
            .tagContent("병원")
            .hospitalTagId(1L)
            .build();
    HospitalTag tag2 = HospitalTag.builder()
            .tagType(HospitalTagType.SERVICE)
            .tagContent("미용")
            .hospitalTagId(1L)
            .build();

    List<HospitalTag> tagList = Arrays.asList(tag1, tag2);

    Hospital hospital = Hospital.builder()
            .storeId(1L)
            .storeName("hospital")
            .additionalServiceTag("zero-pay")
            .build();
    Hospital noRegInfo = Hospital.builder()
            .storeId(2L)
            .storeName("hospital2")
            .thumbnail(new PhotoData("bucket", "object", "photoUrl"))
            .storePhone("010-1234-1234")
            .notice("notice")
            .websiteLink("website")
            .additionalServiceTag("additionalServicetags")
            .storeInfo("storeInfo")
            .storeInfoPhoto(new PhotoData("bucket", "object", "photoUrl"))
            .address(new Address("zipCode", "addressDetail", "sido", "sigungu", "eupmyun", "doro", null))
            .businessHours(Arrays.asList(new BusinessHour(1L, 1, Time.valueOf("09:00:00"), Time.valueOf("18:00:00"), null, null, true, hospital)))
            .registrationInfo(null)
            .build();
    Hospital hospitalSuccess = Hospital.builder()
            .storeId(3L)
            .storeName("hospital2")
            .thumbnail(new PhotoData("bucket", "object", "photoUrl"))
            .storePhone("010-1234-1234")
            .notice("notice")
            .websiteLink("website")
            .additionalServiceTag("additionalServicetags")
            .storeInfo("storeInfo")
            .storeInfoPhoto(new PhotoData("bucket", "object", "photoUrl"))
            .address(new Address("zipCode", "addressDetail", "sido", "sigungu", "eupmyun", "doro", null))
            .businessHours(Arrays.asList(new BusinessHour(1L, 1, Time.valueOf("09:00:00"), Time.valueOf("18:00:00"), null, null, true, hospital)))
            .registrationInfo(RegistrationInfo.builder()
                    .registrationInfoId(1L)
                    .storeType(StoreType.HOSPITAL)
                    .ceoName("ceoName")
                    .ceoPhone("ceoPhone")
                    .ceoEmail("ceoEmail")
                    .businessNumber("number").build())
            .build();


    @Test
    void getHospitalsTest() {
    }

    @Test
    void getHospitalSearch() {
    }

    @Test
    void getHospitalDetail_성공() {


        when(hospitalRepository.findById(hospitalId)).thenReturn(Optional.ofNullable(hospitalSuccess));
        when(hospitalRepository.findTagList(hospitalId)).thenReturn(tagList);

        HospitalDetail hospitalDetail = hospitalServiceImpl.getHospitalDetail(hospitalId);

        assertThat(hospitalDetail).isNotNull();
        // Then
        assertThat(hospitalSuccess.getStoreId()).isEqualTo(3L);
        assertThat(hospitalSuccess.getStoreName()).isEqualTo("hospital2");
        assertThat(hospitalSuccess.getThumbnail()).usingRecursiveComparison().isEqualTo(new PhotoData("bucket", "object", "photoUrl"));
        assertThat(hospitalSuccess.getStorePhone()).isEqualTo("010-1234-1234");
        assertThat(hospitalSuccess.getNotice()).isEqualTo("notice");
        assertThat(hospitalSuccess.getWebsiteLink()).isEqualTo("website");
        assertThat(hospitalSuccess.getStoreInfo()).isEqualTo("storeInfo");
        assertThat(hospitalSuccess.getStoreInfoPhoto()).usingRecursiveComparison().isEqualTo(new PhotoData("bucket", "object", "photoUrl"));
        assertThat(hospitalSuccess.getAddress()).usingRecursiveComparison().isEqualTo(new Address("zipCode", "addressDetail", "sido", "sigungu", "eupmyun", "doro", null));

        List<BusinessHour> businessHours = hospitalSuccess.getBusinessHours();
        assertThat(businessHours).hasSize(1);
        BusinessHour businessHour = businessHours.get(0);
        assertThat(businessHour.getBusinessHourId()).isEqualTo(1L);
        assertThat(businessHour.getDayOfWeek()).isEqualTo(1);
        assertThat(businessHour.getStartTime()).isEqualTo(Time.valueOf("09:00:00"));
        assertThat(businessHour.getEndTime()).isEqualTo(Time.valueOf("18:00:00"));
        assertThat(businessHour.isOpenSt()).isTrue();

        RegistrationInfo registrationInfo = hospitalSuccess.getRegistrationInfo();
        assertThat(registrationInfo).isNotNull();
        assertThat(registrationInfo.getRegistrationInfoId()).isEqualTo(1L);
        assertThat(registrationInfo.getStoreType()).isEqualTo(StoreType.HOSPITAL);
        assertThat(registrationInfo.getCeoName()).isEqualTo("ceoName");
        assertThat(registrationInfo.getCeoPhone()).isEqualTo("ceoPhone");
        assertThat(registrationInfo.getCeoEmail()).isEqualTo("ceoEmail");
        assertThat(registrationInfo.getBusinessNumber()).isEqualTo("number");

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
    void getHospitalDetial_사업자없는경우() {
        when(hospitalRepository.findById(hospitalId)).thenReturn(Optional.ofNullable(hospitalSuccess));
        HospitalDetail hospitalDetail = hospitalServiceImpl.getHospitalDetail(hospitalId);

        assertThat(hospitalDetail).isNotNull();
        assertThat(hospitalDetail.registrationInfo()).isNull();
        assertThat(hospitalDetail.hospitalName()).isEqualTo("hospital2");
    }

    @Test
    void getHospitalDetail_영업시간없는경우() {
        when(hospitalRepository.findById(hospitalId)).thenReturn(Optional.ofNullable(hospital));
        HospitalDetail hospitalDetail = hospitalServiceImpl.getHospitalDetail(hospitalId);

        assertThat(hospitalDetail).isNotNull();
        assertThat(hospitalDetail.businessHours()).isEmpty();
        assertThat(hospitalDetail.hospitalName()).isEqualTo("hospital");
        // Null 일때 빈 리스트
        assertThat(hospitalDetail.additionalServiceTag()).isEqualTo("zero-pay");
    }

    @Test
    void getHospitalDetail_thumbnail없는경우(){
        when(hospitalRepository.findById(hospitalId)).thenReturn(Optional.ofNullable(hospital));
        HospitalDetail hospitalDetail = hospitalServiceImpl.getHospitalDetail(hospitalId);

        assertThat(hospitalDetail).isNotNull();
        assertThat(hospitalDetail.thumbnailUrl()).isEqualTo("default_url");
        assertThat(hospitalDetail.additionalServiceTag()).isEqualTo("zero-pay");
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