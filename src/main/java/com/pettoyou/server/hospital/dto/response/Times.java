package com.pettoyou.server.hospital.dto.response;

import com.pettoyou.server.store.entity.BusinessHour;
import lombok.Builder;

import java.sql.Time;
import java.util.List;
import java.util.Optional;

@Builder
public record Times(

        boolean openSt,

        Time startTime,
        Time endTime,
        Time breakStartTime,
        Time breakEndTime
) {

    public static Times of(List<BusinessHour> businessHours, int dayOfWeek) {
        Optional<BusinessHour> businessHourOpt = businessHours.stream()
                .filter(bh -> bh.getDayOfWeek() == dayOfWeek)
                .findFirst();

        return businessHourOpt.map(bh -> Times.builder()
                        .startTime(bh.getStartTime())
                        .endTime(bh.getEndTime())
                        .breakStartTime(bh.getBreakStartTime())
                        .breakEndTime(bh.getBreakEndTime())
                        .build())
                .orElse(Times.builder().build());
    }

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
