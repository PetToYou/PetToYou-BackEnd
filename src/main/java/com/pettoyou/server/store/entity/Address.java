package com.pettoyou.server.store.entity;

import com.pettoyou.server.constant.entity.BaseEntity;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.geo.Point;

@Getter
@Setter
@Embeddable
public class Address {
//    ZipCode string
//    AddressDetail string
//    Sido string
//    Sigungu string
//    Eupmyun string
//    Doro string
//    Point point

    private String zipCode;
    private String addressDetail;
    private String sido;
    private String sigungu;
    private String eupmyun;
    private String doro;
    private Point point;

}