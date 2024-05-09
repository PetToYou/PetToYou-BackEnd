package com.pettoyou.server.reserve.entity;

import com.pettoyou.server.constant.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "medical_record")
public class MedicalRecord extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "medical_record_id")
    private Long MedicalRecordId;

    private LocalDate clinicDate;

    private String medicalDetail;

    private String caution;

    private String doctor;

    @OneToMany(mappedBy = "medicalRecord", fetch = FetchType.LAZY)
    List<HospitalDocument> documents = new ArrayList<>();

}
