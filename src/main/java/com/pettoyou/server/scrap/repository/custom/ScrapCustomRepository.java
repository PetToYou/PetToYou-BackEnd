package com.pettoyou.server.scrap.repository.custom;

import com.pettoyou.server.scrap.dto.response.ScrapQueryRespDto;

import java.util.List;

public interface ScrapCustomRepository {
    List<ScrapQueryRespDto> findScrapListByMemberId(Long memberId);
}
