package com.pettoyou.server.store.dto;

import com.pettoyou.server.store.entity.BusinessHour;
import com.pettoyou.server.store.entity.enums.StoreType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.sql.Time;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.pettoyou.server.store.entity.BusinessHour}
 */

public class BusinessHourDtos {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Response  {
        LocalDateTime createdAt;
        LocalDateTime modifiedAt;

        @NotNull
        Long businessHourId;
        StoreType storeType;
        @Positive
        Integer dayOfWeek;
        Time startTime;
        Time endTime;
        Time breakStartTime;
        Time breakEndTime;
        boolean openSt;
        Long storeId;


        public static BusinessHourDtos.Response toDto(BusinessHour businessHour){

            return BusinessHourDtos.Response.builder()
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
}