package com.pettoyou.server.reserve.repository;

import com.pettoyou.server.reserve.entity.HospitalDocument;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HospitalDocumentRepository extends JpaRepository<HospitalDocument, Long> {
}
