package com.pettoyou.server.store.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pettoyou.server.constant.entity.BaseEntity;
import com.pettoyou.server.store.entity.enums.StoreType;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Time;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "business_hour")
public class BusinessHour extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "business_hour_id")
    private Long businessHourId;

    @Enumerated(EnumType.STRING)
    private StoreType storeType;

    private Integer dayOfWeek;

    private Time startTime;
    private Time endTime;

    @Nullable
    private Time breakStartTime;
    @Nullable
    private Time breakEndTime;

    private boolean openSt;
    //business_hour은 각각, 월화수목금토일의 상태를 나타내고 openSt=false;는 정기휴무날임을 뜻한다.

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;



}