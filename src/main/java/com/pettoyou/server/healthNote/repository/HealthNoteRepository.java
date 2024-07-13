package com.pettoyou.server.healthNote.repository;

import com.pettoyou.server.healthNote.entity.HealthNote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HealthNoteRepository extends JpaRepository<HealthNote, Long> {
}
