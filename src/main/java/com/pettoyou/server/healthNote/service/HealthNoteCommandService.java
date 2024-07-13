package com.pettoyou.server.healthNote.service;

import com.pettoyou.server.healthNote.dto.request.HealthNoteRegistReqDto;

public interface HealthNoteCommandService {

    void registHealthNote(
            HealthNoteRegistReqDto healthNoteRegistReqDto,
            Long memberId
    );
}
