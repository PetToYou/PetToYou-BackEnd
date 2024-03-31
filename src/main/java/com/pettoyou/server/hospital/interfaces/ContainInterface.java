package com.pettoyou.server.hospital.interfaces;

import com.pettoyou.server.store.entity.BusinessHour;

import java.util.List;

public interface ContainInterface {

    String getStoreId();
    String getHospitalName();

    String getThumbnailUrl();
    Double getDistance() ;
    BusinessHour getBusinessHours();

}
