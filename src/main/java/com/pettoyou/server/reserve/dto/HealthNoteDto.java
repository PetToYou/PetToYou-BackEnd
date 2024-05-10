package com.pettoyou.server.reserve.dto;

import java.time.LocalDate;

public class HealthNoteDto {

    public static class Request {

        public record Register(Long petId, LocalDate clinicDate, String medicalDetail,
                               String caution, String doctor) {
        }
    }

    public static class Response {

    }
}
