package com.pettoyou.server.banner.service.query;

import com.pettoyou.server.banner.dto.response.QueryRespDto;
import com.pettoyou.server.banner.entity.Banner;
import com.pettoyou.server.banner.entity.enums.BannerType;
import com.pettoyou.server.banner.repository.BannerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BannerQueryServiceImpl implements BannerQueryService {
    private final BannerRepository bannerRepository;
    @Override
    public List<QueryRespDto> queryBannersByType(BannerType bannerType) {
        List<QueryRespDto> queryRespDtos = new ArrayList<>();
        List<Banner> bannerByBannerType = bannerRepository.findBannerByBannerType(bannerType);

        for (Banner banner : bannerByBannerType) {
            queryRespDtos.add(QueryRespDto.from(banner));
        }

        return queryRespDtos;
    }
}
