package com.pettoyou.server.pet.repository;

import com.pettoyou.server.pet.entity.Pet;
import com.pettoyou.server.pet.repository.custom.PetCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PetRepository extends JpaRepository<Pet, Long>, PetCustomRepository {
}
