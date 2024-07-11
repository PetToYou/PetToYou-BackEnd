package com.pettoyou.server.hospital.dto.request;

import jakarta.annotation.Nullable;

import java.util.Objects;

public record HospitalQueryCond(
        @Nullable String businessHourCond,
        @Nullable String specialitiesCond,
        @Nullable String filter,
        @Nullable Integer radius,
        @Nullable String openCond,
        @Nullable String emergencyCond
) {
    public HospitalQueryCond {
        if (Objects.isNull(radius)) radius = 5000;
        if (Objects.isNull(openCond)) openCond = "NOT-OPEN";
        if (Objects.isNull(emergencyCond)) emergencyCond = "NOT-EMERGENCY";
    }
}
