package com.pettoyou.server.store.interfaces;

import com.pettoyou.server.store.entity.BusinessHour;

public interface StoreInterface {

    String getStoreId();
    String getHospitalName();

    String getThumbnailUrl();
    Double getDistance() ;
    BusinessHour getBusinessHours();

    Long getReviewCount();

    Double getRateAvg();
}
