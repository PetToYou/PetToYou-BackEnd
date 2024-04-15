package com.pettoyou.server.pet.entity;


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

    private String petProfilePhotoUrl;

    @ManyToOne
    @JoinColumn(name = "pet_id")
    private Pet pet;

    public static PetProfilePhoto toPetProfilePhoto(String photoUrl, Pet pet) {
        return builder()
                .petProfilePhotoUrl(photoUrl)
                .pet(pet)
                .build();
    }
}
