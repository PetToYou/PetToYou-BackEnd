package com.pettoyou.server.store.dto.response;

import java.sql.Time;

public record StoreQueryTotalInfo(
        Long storeId,
        String storeName,
        String thumbnailUrl,
        Time startTime,
        Time endTime,
        Time breakStartTime,
        Time breakEndTime,
        Long reviewCount,
        Double ratingAvg,
        Double distance,
        TagInfo tags
) {
    /***
     * Todo : StoreQueryInfo 와 꼭 나뉘어야하는지 고민해볼 필요가 있음.
     * 그냥 TotalInfo 만 쓰되 인스턴스를 한번 더 생성하는 방법도 괜찮을거같음
      */

}
