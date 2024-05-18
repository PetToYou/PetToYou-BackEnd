package com.pettoyou.server.pet.repository;

import com.pettoyou.server.pet.entity.PetProfilePhoto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetPhotoRepository extends JpaRepository<PetProfilePhoto, Long> {
}
