package com.pettoyou.server.healthNote.repository;

import com.pettoyou.server.healthNote.entity.HealthNote;
import com.pettoyou.server.healthNote.repository.custom.HealthNoteCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HealthNoteRepository extends JpaRepository<HealthNote, Long>, HealthNoteCustomRepository {
}
