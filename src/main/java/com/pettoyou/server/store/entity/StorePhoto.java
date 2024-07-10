package com.pettoyou.server.store.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pettoyou.server.constant.entity.BaseEntity;
import com.pettoyou.server.constant.enums.BaseStatus;
import com.pettoyou.server.photo.entity.PhotoData;
import com.pettoyou.server.store.entity.enums.StoreType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.Where;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE store_photo SET photo_status='DEACTIVATE' WHERE store_photo_id=?")
@SQLRestriction("photo_status = 'ACTIVATE'")
@BatchSize(size = 20)
//fetch join에서의 페이징 처리
@Table(name = "store_photo")
public class StorePhoto extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_photo_id")
    private Long storePhotoId;

    @Enumerated(EnumType.STRING)
    private StoreType storeType;

    @Embedded
    private PhotoData storePhoto;

    private Integer photoOrder;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private BaseStatus photoStatus = BaseStatus.ACTIVATE;

    @ManyToOne
    @JoinColumn(name = "store_id")
    @JsonIgnore
    private Store store;

}
