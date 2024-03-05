package com.pettoyou.server.pet.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class PetProfilePhoto {

    @Id
    @GeneratedValue
    private Long petProfilePhotoId;

    private String petProfilePhotoUrl;

    @ManyToOne
    @JoinColumn(name = "pet_id")
    private Pet pet;

}
