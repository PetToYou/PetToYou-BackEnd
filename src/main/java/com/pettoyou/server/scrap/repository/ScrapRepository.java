package com.pettoyou.server.scrap.repository;

import com.pettoyou.server.scrap.entity.Scrap;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScrapRepository extends JpaRepository<Scrap, Long> {
}
