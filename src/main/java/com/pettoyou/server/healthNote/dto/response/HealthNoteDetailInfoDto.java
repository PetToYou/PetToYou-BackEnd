package com.pettoyou.server.healthNote.dto.response;

import com.pettoyou.server.healthNote.entity.HealthNote;
import lombok.Builder;

import java.time.LocalDate;
import java.util.Objects;

@Builder
public record HealthNoteDetailInfoDto(
        String hospitalName,
        String petName,
        LocalDate visitDate,
        String medicalRecord,
        String caution,
        String vetName
) {
    public static HealthNoteDetailInfoDto of(
            HealthNote healthNote,
            String petName,
            String hospitalName
    ) {
        return HealthNoteDetailInfoDto.builder()
                .hospitalName(hospitalName)
                .petName(petName)
                .visitDate(healthNote.getVisitDate())
                .medicalRecord(healthNote.getMedicalRecord())
                .caution(healthNote.getCaution())
                .vetName(healthNote.getVetName())
                .build();
    }
}
