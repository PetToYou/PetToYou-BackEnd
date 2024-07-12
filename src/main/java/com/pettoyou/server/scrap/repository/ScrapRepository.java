package com.pettoyou.server.scrap.repository;

import com.pettoyou.server.scrap.entity.Scrap;
import com.pettoyou.server.scrap.repository.custom.ScrapCustomRepository;
import com.pettoyou.server.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ScrapRepository extends JpaRepository<Scrap, Long>, ScrapCustomRepository {
}
