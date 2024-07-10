package com.pettoyou.server.hospital.dto.response;

import com.pettoyou.server.store.dto.response.TagInfo;
import lombok.Builder;

import java.sql.Time;

@Builder
public record TestDTO(
        Long storeId,
        String storeName,
        String thumbnailUrl,
        Times time,
        Long reviewCount,
        Double ratingAvg,
        Double distance,
        TagInfo tags
) {
}
