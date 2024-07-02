package com.pettoyou.server.store.repository;

import com.pettoyou.server.store.dto.StorePhotoDtos;
import com.pettoyou.server.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StoreRepository extends JpaRepository<Store, Long> {


    @Query("SELECT StorePhoto FROM StorePhoto WHERE storePhotoId = :storeId ")
    List<StorePhotoDtos> getStorePhotoListByStoreId(Long storeId);
}
