package com.pettoyou.server.hospital.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tag_mapper")
public class TagMapper {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tagMapperId;

    @ManyToOne
    @JoinColumn(name = "hospitalTag_id")
    private HospitalTag hospitalTag;

    @ManyToOne
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;

}
