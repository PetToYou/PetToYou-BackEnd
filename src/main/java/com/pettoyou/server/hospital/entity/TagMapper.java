package com.pettoyou.server.hospital.entity;


import com.pettoyou.server.hospital.entity.Hospital;
import com.pettoyou.server.hospital.entity.HospitalTag;
import com.pettoyou.server.store.entity.Store;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "tag_mapper")
public class TagMapper {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tagMapperId;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hospital_tag_id")
    private HospitalTag hospitalTag;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Hospital hospital;
}
