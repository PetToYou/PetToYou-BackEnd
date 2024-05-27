package com.pettoyou.server.hospital.entity;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hospital_tag_id")
    private HospitalTag hospitalTag;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Hospital hospital;

}
