package com.pettoyou.server.healthNote.entity;

import com.pettoyou.server.constant.entity.BaseEntity;
import com.pettoyou.server.healthNote.dto.request.HealthNoteRegistAndModifyReqDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HealthNote extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "health_note_id")
    private Long healthNoteId;

    @Column(name = "visit_date")
    private LocalDate visitDate;

    @Column(name = "medical_record")
    private String medicalRecord;

    private String caution;

    @Column(name = "vet_name")
    private String vetName;

    @Column(name = "store_id")
    private Long storeId;

    @Column(name = "pet_id")
    private Long petId;

    @Column(name = "member_id")
    private Long memberId;

    public static HealthNote of(HealthNoteRegistAndModifyReqDto registDto, Long memberId) {
        return HealthNote.builder()
                .visitDate(registDto.visitDate())
                .medicalRecord(registDto.medicalRecord())
                .caution(registDto.caution())
                .vetName(registDto.vetName())
                .storeId(registDto.hospitalId())
                .petId(registDto.petId())
                .memberId(memberId)
                .build();
    }

    public void modifyHealthNote(HealthNoteRegistAndModifyReqDto modifyReqDto) {
        this.visitDate = modifyReqDto.visitDate();
        this.medicalRecord = modifyReqDto.medicalRecord();
        this.caution = modifyReqDto.caution();
        this.vetName = modifyReqDto.vetName();
        this.storeId = modifyReqDto.hospitalId();
        this.petId = modifyReqDto.petId();
    }
}
