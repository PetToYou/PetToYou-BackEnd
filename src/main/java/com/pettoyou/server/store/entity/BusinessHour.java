package com.pettoyou.server.store.entity;

import com.pettoyou.server.store.entity.enums.StoreType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;

@Getter
@Setter
@Entity
@Table(name = "business_hour")
public class BusinessHour {

    @Id
    @GeneratedValue
    private Long businessHourId;

    @Enumerated(EnumType.STRING)
    private StoreType storeType;

    private Integer dayOfWeek;

    private Time startTime;
    private Time endTime;
    private Time breakStartTime;
    private Time breakEndTime;
//    private boolean isOpen;

}