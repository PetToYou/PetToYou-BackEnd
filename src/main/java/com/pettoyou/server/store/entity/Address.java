package com.pettoyou.server.store.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.locationtech.jts.geom.Point;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Address {


    private String zipCode;
    private String addressDetail;
    private String sido;
    private String sigungu;

    @Column(nullable = true)
    private String eupmyun;

    private String doro;

    @JsonSerialize(using = PointSerializer.class)
    @NotNull
    @Column(nullable = true, columnDefinition = "POINT SRID 4326")
    private Point point;
    //SRID 4326은 위도 경도 순으로  y, x

}