package com.pettoyou.server.reserve.repository;

import com.pettoyou.server.reserve.entity.HealthNote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HealthNoteRepository extends JpaRepository<HealthNote, Long> {
}
