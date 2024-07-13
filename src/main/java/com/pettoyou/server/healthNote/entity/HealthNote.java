package com.pettoyou.server.healthNote.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HealthNote {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "health_note_id")
    private Long healthNoteId;

    @Column(name = "visit_date")
    private LocalDate visitDate;

    private String caution;

    @Column(name = "store_id")
    private Long storeId;

    @Column(name = "pet_id")
    private Long petId;

    @Column(name = "vet_name")
    private String vetName;
}
