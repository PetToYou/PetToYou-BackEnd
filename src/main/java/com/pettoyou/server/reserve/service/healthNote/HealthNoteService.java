package com.pettoyou.server.reserve.service.healthNote;

import com.pettoyou.server.reserve.dto.HealthNoteDto;

public interface HealthNoteService {

    void register(HealthNoteDto.Request.Register registerDto);
}
