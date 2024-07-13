package com.pettoyou.server.healthNote.dto.request;

import jakarta.annotation.Nullable;

import java.time.LocalDate;

public record HealthNoteRegistAndModifyReqDto(
        Long hospitalId,
        Long petId,
        LocalDate visitDate,
        String medicalRecord,
        String caution,
        @Nullable String vetName
) {
}
