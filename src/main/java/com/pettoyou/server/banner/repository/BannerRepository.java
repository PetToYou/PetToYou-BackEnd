package com.pettoyou.server.banner.repository;

import com.pettoyou.server.banner.entity.Banner;
import com.pettoyou.server.banner.entity.enums.BannerType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BannerRepository extends JpaRepository<Banner, Long> {
}
