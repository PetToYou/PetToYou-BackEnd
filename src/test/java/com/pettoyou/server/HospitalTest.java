package com.pettoyou.server;

import com.pettoyou.server.hospital.entity.Hospital;
import com.pettoyou.server.hospital.repository.HospitalRepository;
import com.pettoyou.server.store.entity.enums.StoreType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceUnit;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class HospitalTest {

    @PersistenceUnit
    EntityManager em;

    @Autowired
    HospitalRepository hospitalRepository;

    @Test
    @Transactional
    public void register() throws Exception {
        Hospital hospital = Hospital.builder()
                .storeName("test")
                .additionalServiceTag("호텔")
                .build();

        em.persist(hospital);
        em.flush();
        em.clear();

        List<Hospital> hospitalList = hospitalRepository.findAll();

        assertThat(hospitalList.get(1).getStoreType()).isEqualTo(StoreType.HOSPITAL);
        //자동으로 storeType이 저장되는지 확인

    }
}