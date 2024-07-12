package com.pettoyou.server.scrap.service;

import com.pettoyou.server.scrap.dto.response.ScrapQueryRespDto;
import com.pettoyou.server.scrap.dto.response.ScrapRegistRespDto;

import java.util.List;

public interface ScrapService {

    ScrapRegistRespDto scrapRegist(Long storeId, Long memberId);

    void scrapCancel(Long scrapId, Long memberId);

    List<ScrapQueryRespDto> fetchScrapStore(Long memberId);
}
