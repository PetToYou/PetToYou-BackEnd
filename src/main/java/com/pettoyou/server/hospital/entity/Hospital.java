package com.pettoyou.server.hospital.entity;


import com.pettoyou.server.constant.enums.BaseStatus;
import com.pettoyou.server.review.entity.Review;
import com.pettoyou.server.store.entity.Address;
import com.pettoyou.server.store.entity.Store;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@DiscriminatorValue("H")
@Table(name = "hospital", indexes = {
        @Index(name = "idx_hospital_name", columnList = "hospitalName")
})
public class Hospital extends Store
{
//    @Id
//    @GeneratedValue
//    private Long hospitalId;

    @Column(nullable = false)
    private String hospitalName;

    private String hospitalPhone;

    private String notice;

    private String additionalServiceTag;

    private String websiteLink;

    private String hospitalInfo;

    private String hospitalInfoPhoto;

    @Enumerated(EnumType.STRING)
    private BaseStatus hospitalStatus;

}
