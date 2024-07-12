package com.pettoyou.server.scrap.controller;

import com.pettoyou.server.config.security.service.PrincipalDetails;
import com.pettoyou.server.constant.dto.ApiResponse;
import com.pettoyou.server.constant.enums.CustomResponseStatus;
import com.pettoyou.server.scrap.dto.request.ScrapRegistReqDto;
import com.pettoyou.server.scrap.dto.response.ScrapQueryRespDto;
import com.pettoyou.server.scrap.dto.response.ScrapRegistRespDto;
import com.pettoyou.server.scrap.service.ScrapService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @DeleteMapping("/scrap/{scrapId}")
    public ResponseEntity<ApiResponse<String>> scrapRegister(
            @PathVariable Long scrapId,
            @AuthenticationPrincipal PrincipalDetails principalDetails
    ) {
        scrapService.scrapCancel(scrapId, principalDetails.getUserId());
        return ResponseEntity.ok().body(ApiResponse.createSuccess("삭제 완료", CustomResponseStatus.SUCCESS));
    }

    @GetMapping("/scraps")
    public ResponseEntity<ApiResponse<List<ScrapQueryRespDto>>> fetchScrapList (
            @AuthenticationPrincipal PrincipalDetails principalDetails
    ) {
        List<ScrapQueryRespDto> response = scrapService.fetchScrapStore(principalDetails.getUserId());
        return ResponseEntity.ok().body(ApiResponse.createSuccess(response, CustomResponseStatus.SUCCESS));
    }
}
