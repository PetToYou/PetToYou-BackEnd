package com.pettoyou.server.hospital.repository;

import com.pettoyou.server.hospital.entity.Hospital;
import com.pettoyou.server.hospital.repository.custom.HospitalCustomRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface HospitalRepository extends JpaRepository<Hospital, Long>, HospitalCustomRepository {


    @Query("SELECT h.storeName FROM Hospital h WHERE h.storeId = :hospitalId")
    String getHospitalNameNameByStoreId(Long hospitalId);

}
