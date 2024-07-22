package com.pettoyou.server.hospital.dto.response;

import com.pettoyou.server.store.entity.BusinessHour;
import lombok.Builder;

import java.sql.Time;

@Builder
public record Times(

        boolean openSt,

        Time startTime,
        Time endTime,
        Time breakStartTime,
        Time breakEndTime
) {

    public static Times of(BusinessHour businessHour){


        return Times.builder()
                .openSt(businessHour.isOpenSt())
                .startTime(businessHour.getStartTime())
                .endTime(businessHour.getEndTime())
                .breakStartTime(businessHour.getBreakStartTime())
                .breakEndTime(businessHour.getBreakEndTime())
                .build();
    }
}
