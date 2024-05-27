package com.pettoyou.server.banner.service;

import com.pettoyou.server.banner.dto.request.BannerRegisterRequestDto;
import com.pettoyou.server.banner.dto.response.BannerRegisterResponseDto;
import com.pettoyou.server.banner.entity.Banner;
import com.pettoyou.server.banner.repository.BannerRepository;
import com.pettoyou.server.photo.entity.PhotoData;
import com.pettoyou.server.util.S3Util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class BannerServiceImpl implements BannerService{
    private final BannerRepository bannerRepository;
    private final S3Util s3Util;
    @Override
    public BannerRegisterResponseDto bannerRegister(
            BannerRegisterRequestDto bannerRegisterRequestDto,
            MultipartFile bannerImg
    ) {
        PhotoData bannerImgData = s3Util.uploadFile(bannerImg);
        Banner save = bannerRepository.save(Banner.of(bannerRegisterRequestDto, bannerImgData));

        return BannerRegisterResponseDto.builder()
                .bannerId(save.getBannerId())
                .build();
    }
}
