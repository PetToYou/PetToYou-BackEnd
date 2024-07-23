package com.pettoyou.server.banner.service.query;

import com.pettoyou.server.banner.dto.response.BannerQueryRespDto;
import com.pettoyou.server.banner.entity.enums.BannerType;

import java.util.List;

public interface BannerQueryService {

    List<BannerQueryRespDto> queryBannersByType();
}
