package com.pettoyou.server.store.repository;

import com.pettoyou.server.store.entity.StorePhoto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StorePhotoRepository extends JpaRepository<StorePhoto, Long> {

}