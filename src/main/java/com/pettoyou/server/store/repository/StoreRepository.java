package com.pettoyou.server.store.repository;

import com.pettoyou.server.store.dto.StorePhotoDto;
import com.pettoyou.server.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StoreRepository extends JpaRepository<Store, Long> {


    @Query("SELECT StorePhoto FROM StorePhoto WHERE storePhotoId = :storeId ")
    List<StorePhotoDto> getStorePhotoListByStoreId(Long StoreId storeId);
}
