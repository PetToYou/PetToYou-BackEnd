package com.pettoyou.server.store.repository;

import com.pettoyou.server.store.entity.Store;
import com.pettoyou.server.store.repository.custom.StorePhotoCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StoreRepository extends JpaRepository<Store, Long>, StorePhotoCustomRepository {



    @Query("SELECT s.storeName FROM Store s WHERE s.storeId = :storeId")
    String getStoreNameByStoreId(Long storeId);
}
