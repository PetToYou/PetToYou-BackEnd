package com.pettoyou.server.pet.entity;


import com.pettoyou.server.photo.entity.FileData;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "pet_profile_photo")
public class PetProfilePhoto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long petProfilePhotoId;

    @ManyToOne
    @JoinColumn(name = "pet_id")
    private Pet pet;

    @Embedded
    private FileData fileData;

    public static PetProfilePhoto toPetProfilePhoto(FileData fileData, Pet pet) {
        return builder()
                .fileData(fileData)
                .pet(pet)
                .build();
    }
}
