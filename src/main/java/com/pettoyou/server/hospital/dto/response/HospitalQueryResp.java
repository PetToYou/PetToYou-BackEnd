package com.pettoyou.server.hospital.dto.response;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Objects;

public record HospitalQueryResp(
        String storeId,
        @NotNull String hospitalName,
        String thumbnailUrl,
        String distance,
        String businessHourStatus,
        String openHour,
        String closeHour,
        @Nullable String breakStatTime,
        @Nullable String breakEndTime,
        Long reviewCount,
        Double rateAvg
) {

    // 컴팩트 생성자를 이용해서 기존의 Builder.Default를 적용함
    // Todo : 기본 Thunmbnail URL이 정해지면 교체해야함.
    @Builder
    public HospitalQueryResp {
        if (Objects.isNull(thumbnailUrl)) thumbnailUrl = "PetToYou-Logo.png";
        if (Objects.isNull(businessHourStatus)) businessHourStatus = "몰라";
        if (Objects.isNull(openHour)) openHour = LocalDateTime.now().toString();
        if (Objects.isNull(closeHour)) closeHour = LocalDateTime.now().toString();
        if (Objects.isNull(reviewCount)) reviewCount = 0L;
        if (Objects.isNull(rateAvg)) rateAvg = 0.0;
    }
}
