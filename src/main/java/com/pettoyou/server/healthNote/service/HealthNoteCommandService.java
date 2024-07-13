package com.pettoyou.server.healthNote.service;

import com.pettoyou.server.healthNote.dto.request.HealthNoteRegistAndModifyReqDto;

public interface HealthNoteCommandService {

    void registHealthNote(
            HealthNoteRegistAndModifyReqDto healthNoteRegistAndModifyReqDto,
            Long authMemberId
    );

    void modifyHealthNote(
            Long healthNoteId,
            HealthNoteRegistAndModifyReqDto healthNoteRegistAndModifyReqDto,
            Long authMemberId
    );
}
