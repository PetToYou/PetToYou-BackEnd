package com.pettoyou.server.store.repository;

import com.pettoyou.server.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long> {
}
