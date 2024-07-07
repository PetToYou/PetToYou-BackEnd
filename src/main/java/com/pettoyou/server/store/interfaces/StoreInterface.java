package com.pettoyou.server.store.interfaces;

import com.pettoyou.server.photo.entity.PhotoData;
import com.pettoyou.server.store.entity.BusinessHour;
import jakarta.persistence.Embedded;

public interface StoreInterface {

    String getStoreId();
    String getHospitalName();

    @Embedded
    PhotoData getThumbnail();
    Double getDistance() ;
    BusinessHour getBusinessHours();

    Long getReviewCount();

    Double getRateAvg();
}
