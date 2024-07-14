package com.pettoyou.server.review.controller;

import com.pettoyou.server.config.security.service.PrincipalDetails;
import com.pettoyou.server.constant.dto.ApiResponse;
import com.pettoyou.server.constant.enums.CustomResponseStatus;
import com.pettoyou.server.review.dto.ReviewReqDto;
import com.pettoyou.server.review.dto.ReviewRespDto;
import com.pettoyou.server.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Slf4j
public class ReviewController {

    ReviewService reviewService;

    @PostMapping("/member/store/{storeId}/review")
    public ResponseEntity<ApiResponse<String>> registerReview(
            @PathVariable Long storeId,
            @RequestParam Long petId,
            @RequestPart(required = false,value = "reviewImgs") List<MultipartFile> reviewImgs,
            @RequestPart(value = "reviewDto") ReviewReqDto reviewReqDto,
            @AuthenticationPrincipal PrincipalDetails principalDetails
            ) {
        Long userId = principalDetails.getUserId();

        String reviewId = reviewService.registerReiview(storeId,petId, userId, reviewImgs, reviewReqDto);

        String path = ServletUriComponentsBuilder.fromCurrentRequest().build().getPath();
        URI location = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(path + "/{reviewId}")
                .buildAndExpand(reviewId)  // reviewId 변수를 정확히 전달
                .toUri();
        return ResponseEntity.created(location).body(ApiResponse.createSuccess("등록 완료", CustomResponseStatus.SUCCESS));
    }

    @GetMapping("/store/{storeId}/review")
    public ResponseEntity<ApiResponse<Page<ReviewRespDto>>> getReview(@PathVariable Long storeId,
                                                                      @PageableDefault(size = 10, sort = "created_at", direction = Sort.Direction.DESC) Pageable pageable)
    {
        Page<ReviewRespDto> reviewRespDto = reviewService.getReview(storeId, pageable);


        return ResponseEntity.ok().body(ApiResponse.createSuccess(reviewRespDto, CustomResponseStatus.SUCCESS));
    }




    //상단고정 기능
    //수정기능
    //삭제기능
    //리뷰 신고기능
}
