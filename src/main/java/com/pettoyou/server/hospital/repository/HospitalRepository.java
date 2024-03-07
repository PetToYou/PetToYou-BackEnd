package com.pettoyou.server.hospital.repository;

import com.pettoyou.server.hospital.entity.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HospitalRepository extends JpaRepository<Hospital, Long> {
}
