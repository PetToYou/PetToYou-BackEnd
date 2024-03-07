package com.pettoyou.server.reserve.repository;

import com.pettoyou.server.reserve.entity.Reserve;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReserveRepository extends JpaRepository<Reserve, Long> {
}
