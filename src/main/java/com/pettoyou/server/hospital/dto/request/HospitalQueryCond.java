package com.pettoyou.server.hospital.dto.request;

import jakarta.annotation.Nullable;

import java.util.Objects;

public record HospitalQueryCond(
        @Nullable String businessHourCond,
        @Nullable Integer distanceCond, // 프론트에서 보내줄때 화면에서 보이는것과 달리 meter 단위로 보내줘야함.
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
