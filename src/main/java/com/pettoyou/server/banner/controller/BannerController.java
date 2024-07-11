package com.pettoyou.server.banner.controller;

import com.pettoyou.server.banner.dto.request.BannerRegisterRequestDto;
import com.pettoyou.server.banner.dto.response.BannerRegisterResponseDto;
import com.pettoyou.server.banner.dto.response.BannerQueryRespDto;
import com.pettoyou.server.banner.entity.enums.BannerType;
import com.pettoyou.server.banner.service.BannerService;
import com.pettoyou.server.banner.service.query.BannerQueryService;
import com.pettoyou.server.constant.dto.ApiResponse;
import com.pettoyou.server.constant.enums.CustomResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class BannerController {
    private final BannerService bannerService;
    private final BannerQueryService bannerQueryService;

    // 배너 등록
    @PostMapping("admin/banner")
    public ResponseEntity<ApiResponse<BannerRegisterResponseDto>> bannerRegister(
            @RequestPart(value = "bannerRequestDto") BannerRegisterRequestDto bannerRegisterRequestDto,
            @RequestPart(value = "bannerImg") MultipartFile bannerImg
    ) {

        BannerRegisterResponseDto response = bannerService.bannerRegister(bannerRegisterRequestDto, bannerImg);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(response, CustomResponseStatus.SUCCESS));
    }

    // 배너 수정
    @PutMapping("admin/banner/{bannerId}")
    public ResponseEntity<ApiResponse<BannerRegisterResponseDto>> bannerModify(
            @RequestPart(value = "bannerRequestDto") BannerRegisterRequestDto bannerRegisterRequestDto,
            @RequestPart(value = "bannerImg") MultipartFile bannerImg,
            @PathVariable Long bannerId
    ) {
        BannerRegisterResponseDto response = bannerService.bannerModify(bannerRegisterRequestDto, bannerImg, bannerId);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(response, CustomResponseStatus.SUCCESS));
    }

    // 배너 삭제
    @DeleteMapping("admin/banner/{bannerId}")
    public ResponseEntity<ApiResponse<String>> bannerModify(
            @PathVariable Long bannerId
    ) {
        bannerService.bannerDelete(bannerId);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(null, CustomResponseStatus.SUCCESS));
    }

    @GetMapping("/banners")
    public ResponseEntity<ApiResponse<List<BannerQueryRespDto>>> bannerQueryByBannerType(
            @RequestParam BannerType bannerType
    ) {
        List<BannerQueryRespDto> response = bannerQueryService.queryBannersByType(bannerType);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(response, CustomResponseStatus.SUCCESS));
    }
}
