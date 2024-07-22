package com.pettoyou.server.store.repository;

import com.pettoyou.server.store.entity.StorePhoto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StorePhotoRepository extends JpaRepository<StorePhoto, Long> {
}