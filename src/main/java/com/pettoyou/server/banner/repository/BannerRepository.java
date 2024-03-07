package com.pettoyou.server.banner.repository;

import com.pettoyou.server.banner.entity.Banner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BannerRepository extends JpaRepository<Banner, Long> {
}
