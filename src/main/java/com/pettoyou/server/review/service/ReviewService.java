package com.pettoyou.server.review.service;

import com.pettoyou.server.review.dto.ReviewReqDto;
import com.pettoyou.server.review.dto.ReviewRespDto;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.Page;
import java.util.List;


public interface ReviewService {

    String registerReiview(Long storeId, Long petId, Long userId, List<MultipartFile> reviewImgs, ReviewReqDto reviewReqDto);

    Page<ReviewRespDto> getReview(Long storeId, Pageable pageable);
}
