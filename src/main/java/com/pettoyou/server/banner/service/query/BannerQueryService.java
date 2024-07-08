package com.pettoyou.server.banner.service.query;

import com.pettoyou.server.banner.dto.response.QueryRespDto;
import com.pettoyou.server.banner.entity.enums.BannerType;

import java.util.List;

public interface BannerQueryService {

    List<QueryRespDto> queryBannersByType(BannerType bannerType);
}
