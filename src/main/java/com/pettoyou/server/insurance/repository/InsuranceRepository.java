package com.pettoyou.server.insurance.repository;

import com.pettoyou.server.insurance.entity.Insurance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InsuranceRepository extends JpaRepository<Insurance, Long> {
}
