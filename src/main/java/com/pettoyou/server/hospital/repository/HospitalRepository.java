package com.pettoyou.server.hospital.repository;

import com.pettoyou.server.hospital.entity.Hospital;
import com.pettoyou.server.hospital.repository.custom.HospitalCustomRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HospitalRepository extends JpaRepository<Hospital, Long>, HospitalCustomRepository {
  @EntityGraph(
    attributePaths = { "storePhotos" },
    type = EntityGraph.EntityGraphType.LOAD
  )
  Optional<Hospital> findDistinctHospitalByStoreId(Long hospitalId);
}
