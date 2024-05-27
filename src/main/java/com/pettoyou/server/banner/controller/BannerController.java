package com.pettoyou.server.banner.controller;

import com.pettoyou.server.banner.dto.request.BannerRegisterRequestDto;
import com.pettoyou.server.banner.dto.response.BannerRegisterResponseDto;
import com.pettoyou.server.banner.service.BannerService;
import com.pettoyou.server.constant.dto.ApiResponse;
import com.pettoyou.server.constant.enums.CustomResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class BannerController {
    private final BannerService bannerService;

    // 배너 등록
    @PostMapping("/banner")
    public ResponseEntity<ApiResponse<BannerRegisterResponseDto>> registerBanner(
            @RequestPart(value = "bannerRequestDto") BannerRegisterRequestDto bannerRegisterRequestDto,
            @RequestPart(value = "bannerImg")MultipartFile bannerImg
            ) {

        BannerRegisterResponseDto response = bannerService.bannerRegister(bannerRegisterRequestDto, bannerImg);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(response, CustomResponseStatus.SUCCESS));
    }

    // 배너 수정

    // 배너 삭제
}
