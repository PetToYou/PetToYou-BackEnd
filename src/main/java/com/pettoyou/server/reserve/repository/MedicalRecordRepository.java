package com.pettoyou.server.reserve.repository;

import com.pettoyou.server.reserve.entity.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {
}
