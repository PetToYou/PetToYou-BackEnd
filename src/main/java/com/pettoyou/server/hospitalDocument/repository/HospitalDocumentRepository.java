package com.pettoyou.server.hospitalDocument.repository;

import com.pettoyou.server.hospitalDocument.entity.HospitalDocument;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HospitalDocumentRepository extends JpaRepository<HospitalDocument, Long> {
}
