package com.pettoyou.server.hospital.dto.request;

public record HospitalQueryAddressInfo(
        double latitude,
        double longitude
) {

    public String toPointString() {
        return String.format("POINT (%f %f)", this.latitude, this.longitude);
    }

}
