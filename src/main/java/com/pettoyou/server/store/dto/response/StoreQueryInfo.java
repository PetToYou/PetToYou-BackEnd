package com.pettoyou.server.store.dto.response;

import java.sql.Time;
import java.time.LocalTime;

public record StoreQueryInfo(
        Long storeId,
        String storeName,
        String thumbnailUrl,
        Time startTime,
        Time endTime,
        Time breakStartTime,
        Time breakEndTime,
        Long reviewCount,
        Double ratingAvg,
        Double distance
) {

    public StoreQueryInfo(Long storeId, String storeName, String thumbnailUrl, Time startTime, Time endTime, Time breakStartTime, Time breakEndTime, Long reviewCount, Double ratingAvg, Double distance) {
        this.storeId = storeId;
        this.storeName = storeName;
        this.thumbnailUrl = thumbnailUrl;
        this.startTime = startTime != null ? startTime : Time.valueOf(LocalTime.of(9, 0));
        this.endTime = endTime != null ? endTime : Time.valueOf(LocalTime.of(18, 0));
        this.breakStartTime = breakStartTime;
        this.breakEndTime = breakEndTime;
        this.reviewCount = reviewCount != null ? reviewCount : 0;
        this.ratingAvg = ratingAvg != null ? ratingAvg : 0.0;
        this.distance = Math.round((distance / 1000.0) * 10) / 10.0;
    }
}
