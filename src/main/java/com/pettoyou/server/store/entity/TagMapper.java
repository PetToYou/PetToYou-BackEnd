package com.pettoyou.server.store.entity;

import com.pettoyou.server.hospital.entity.HospitalTag;
import com.pettoyou.server.store.entity.Store;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "tag_mapper")
public class TagMapper {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tagMapperId;

    @ManyToOne
    @JoinColumn(name = "hospital_tag_id")
    private HospitalTag hospitalTag;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

}
