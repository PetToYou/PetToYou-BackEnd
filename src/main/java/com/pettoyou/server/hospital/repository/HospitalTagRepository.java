package com.pettoyou.server.hospital.repository;

import com.pettoyou.server.hospital.entity.HospitalTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HospitalTagRepository extends JpaRepository<HospitalTag, Long> {
}