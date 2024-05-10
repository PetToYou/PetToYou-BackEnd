package com.pettoyou.server.hospitalDocument.entity;

import com.pettoyou.server.photo.entity.FileData;
import com.pettoyou.server.reserve.entity.HealthNote;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "hospital_document")
public class HospitalDocument {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hospital_document_id")
    private Long hospitalDocumentId;

    private String fileName;

    private FileData fileData;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medical_record_id")
    private HealthNote healthNote;
}
