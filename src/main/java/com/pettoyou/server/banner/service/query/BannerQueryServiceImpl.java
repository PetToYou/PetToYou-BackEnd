package com.pettoyou.server.banner.service.query;

import com.pettoyou.server.banner.dto.response.BannerQueryRespDto;
import com.pettoyou.server.banner.entity.Banner;
import com.pettoyou.server.banner.entity.enums.BannerType;
import com.pettoyou.server.banner.repository.BannerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BannerQueryServiceImpl implements BannerQueryService {
    private final BannerRepository bannerRepository;
    @Override
    public List<BannerQueryRespDto> queryBannersByType(BannerType bannerType) {
        List<Banner> bannersByBannerType = bannerRepository.findBannerByBannerType(bannerType);

        return bannersByBannerType.stream().map(BannerQueryRespDto::from).toList();
    }
}
