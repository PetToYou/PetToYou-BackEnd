package com.pettoyou.server.banner.service;

import com.pettoyou.server.banner.dto.request.BannerRegisterRequestDto;
import com.pettoyou.server.banner.dto.response.BannerRegisterResponseDto;
import org.springframework.web.multipart.MultipartFile;

public interface BannerService {

    BannerRegisterResponseDto bannerRegister(
            BannerRegisterRequestDto bannerRegisterRequestDto,
            MultipartFile bannerImg);

    BannerRegisterResponseDto bannerModify(
            BannerRegisterRequestDto bannerRegisterRequestDto,
            MultipartFile bannerImg,
            Long bannerId);
}
