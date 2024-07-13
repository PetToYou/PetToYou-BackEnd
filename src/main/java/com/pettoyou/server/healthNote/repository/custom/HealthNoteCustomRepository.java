package com.pettoyou.server.healthNote.repository.custom;

import com.pettoyou.server.healthNote.dto.response.HealthNoteSimpleInfoDto;
import com.pettoyou.server.healthNote.entity.HealthNote;

import java.util.List;

public interface HealthNoteCustomRepository {

    List<HealthNoteSimpleInfoDto> findHealthNotesByPetId(Long petId);
}
