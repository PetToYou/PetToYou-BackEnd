package com.pettoyou.server.reserve.entity;

import com.pettoyou.server.constant.entity.BaseEntity;
import com.pettoyou.server.hospitalDocument.entity.HospitalDocument;
import com.pettoyou.server.pet.entity.Pet;
import com.pettoyou.server.reserve.dto.HealthNoteDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "health_note")
public class HealthNote extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "health_note_id")
    private Long HealthNoteId;

    private LocalDate clinicDate;

    private String medicalDetail;

    private String caution;

    private String doctor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id")
    private Pet pet;

    @OneToMany(mappedBy = "healthNote", fetch = FetchType.LAZY)
    List<HospitalDocument> documents = new ArrayList<>();

    public static HealthNote toEntity(HealthNoteDto.Request.Register registerDto, Pet pet) {
        return builder()
                .clinicDate(registerDto.clinicDate())
                .medicalDetail(registerDto.medicalDetail())
                .caution(registerDto.caution())
                .doctor(registerDto.doctor())
                .pet(pet)
                .build();
    }

}
