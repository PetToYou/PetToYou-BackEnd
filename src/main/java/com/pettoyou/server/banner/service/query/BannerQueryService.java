package com.pettoyou.server.banner.service.query;

import com.pettoyou.server.banner.dto.response.BannerQueryRespDto;

import java.util.List;

public interface BannerQueryService {

    List<BannerQueryRespDto> queryBanners();
}
