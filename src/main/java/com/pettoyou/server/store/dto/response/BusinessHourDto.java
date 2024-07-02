package com.pettoyou.server.store.dto.response;

import com.pettoyou.server.store.entity.BusinessHour;
import com.pettoyou.server.store.entity.enums.StoreType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

import java.sql.Time;
import java.time.LocalDateTime;

@Builder
public record BusinessHourDto(
        LocalDateTime createdAt,
        LocalDateTime modifiedAt,
        @NotNull Long businessHourId,
        StoreType storeType,
        @Positive Integer dayOfWeek,
        Time startTime,
        Time endTime,
        Time breakStartTime,
        Time breakEndTime,
        boolean openSt,
        Long storeId
) {
    public static BusinessHourDto toDto(BusinessHour businessHour) {
        return BusinessHourDto.builder()
                .createdAt(businessHour.getCreatedAt())
                .modifiedAt(businessHour.getModifiedAt())
                .businessHourId(businessHour.getBusinessHourId())
                .storeType(businessHour.getStoreType())
                .dayOfWeek(businessHour.getDayOfWeek())
                .startTime(businessHour.getStartTime())
                .endTime(businessHour.getEndTime())
                .breakStartTime(businessHour.getBreakStartTime())
                .breakEndTime(businessHour.getBreakEndTime())
                .openSt(businessHour.isOpenSt())
                .storeId(businessHour.getStore().getStoreId())
                .build();
    }

}
