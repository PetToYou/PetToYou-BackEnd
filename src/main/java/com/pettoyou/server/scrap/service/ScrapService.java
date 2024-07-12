package com.pettoyou.server.scrap.service;

import com.pettoyou.server.scrap.dto.response.ScrapRegistRespDto;

public interface ScrapService {

    ScrapRegistRespDto scrapRegist(Long storeId, Long memberId);
}
