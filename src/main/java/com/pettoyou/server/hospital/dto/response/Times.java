package com.pettoyou.server.hospital.dto.response;

import lombok.Builder;

import java.sql.Time;

@Builder
public record Times(
        Time startTime,
        Time endTime,
        Time breakStartTime,
        Time breakEndTime
) {
    public static Times of(Time startTime, Time endTime, Time breakStartTime, Time breakEndTime) {
        return Times.builder()
                        .startTime(startTime)
                        .endTime(endTime)
                        .breakStartTime(breakStartTime)
                        .breakEndTime(breakEndTime)
                        .build();
    }
}
