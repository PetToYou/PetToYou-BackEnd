package com.pettoyou.server.hospital.dto.request;

import java.util.Objects;

public record HospitalQueryInfo(
        double latitude,
        double longitude,
        Integer radius
) {

    public HospitalQueryInfo {
        if (Objects.isNull(radius)) radius = 5000;
    }

    public String toPointString() {
        return String.format("POINT (%f %f)", this.latitude, this.longitude);
    }

}
