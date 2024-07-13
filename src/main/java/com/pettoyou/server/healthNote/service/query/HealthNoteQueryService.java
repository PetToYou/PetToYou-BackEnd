package com.pettoyou.server.healthNote.service.query;

import com.pettoyou.server.healthNote.dto.response.HealthNoteSimpleInfoDto;

import java.util.List;

public interface HealthNoteQueryService {

    List<HealthNoteSimpleInfoDto> fetchHealthNotesByPetId(
            Long petId,
            Long authMemberId
    );
}
