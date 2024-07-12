package com.pettoyou.server.scrap.controller;

import com.pettoyou.server.config.security.service.PrincipalDetails;
import com.pettoyou.server.constant.dto.ApiResponse;
import com.pettoyou.server.constant.enums.CustomResponseStatus;
import com.pettoyou.server.scrap.dto.request.ScrapRegistReqDto;
import com.pettoyou.server.scrap.dto.response.ScrapRegistRespDto;
import com.pettoyou.server.scrap.service.ScrapService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member/")
public class ScrapController {
    private final ScrapService scrapService;

    @PostMapping("/scrap")
    public ResponseEntity<ApiResponse<ScrapRegistRespDto>> scrapRegister(
            @RequestBody ScrapRegistReqDto scrapRegistReqDto,
            @AuthenticationPrincipal PrincipalDetails principalDetails
    ) {
        ScrapRegistRespDto response = scrapService.scrapRegist(scrapRegistReqDto.storeId(), principalDetails.getUserId());
        return ResponseEntity.ok().body(ApiResponse.createSuccess(response, CustomResponseStatus.SUCCESS));
    }
}
