package com.pettoyou.server.banner.service;

import com.pettoyou.server.banner.dto.request.BannerRegisterRequestDto;
import com.pettoyou.server.banner.dto.response.BannerRegisterResponseDto;
import com.pettoyou.server.banner.entity.Banner;
import com.pettoyou.server.banner.repository.BannerRepository;
import com.pettoyou.server.constant.enums.CustomResponseStatus;
import com.pettoyou.server.constant.exception.CustomException;
import com.pettoyou.server.photo.entity.PhotoData;
import com.pettoyou.server.util.S3Util;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional
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

    @Override
    public BannerRegisterResponseDto bannerModify(BannerRegisterRequestDto bannerRegisterRequestDto, MultipartFile bannerImg, Long bannerId) {
        Banner banner = bannerRepository.findById(bannerId)
                .orElseThrow(() -> new CustomException(CustomResponseStatus.BANNER_NOT_FOUND));

        s3Util.deleteFile(banner.getImgBucket(), banner.getImgKey());
        banner.bannerModify(bannerRegisterRequestDto, s3Util.uploadFile(bannerImg));

        return BannerRegisterResponseDto.builder()
                .bannerId(banner.getBannerId())
                .build();
    }

    @Override
    public void bannerDelete(Long bannerId) {
        Banner banner = bannerRepository.findById(bannerId)
                .orElseThrow(() -> new CustomException(CustomResponseStatus.BANNER_NOT_FOUND));

        bannerRepository.delete(banner);
    }
}
