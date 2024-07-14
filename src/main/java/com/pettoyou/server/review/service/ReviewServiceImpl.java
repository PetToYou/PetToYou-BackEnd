package com.pettoyou.server.review.service;

import com.pettoyou.server.constant.enums.CustomResponseStatus;
import com.pettoyou.server.constant.exception.CustomException;
import com.pettoyou.server.pet.entity.Pet;
import com.pettoyou.server.pet.repository.PetRepository;
import com.pettoyou.server.review.dto.ReviewReqDto;
import com.pettoyou.server.review.dto.ReviewRespDto;
import com.pettoyou.server.review.entity.Review;
import com.pettoyou.server.review.repository.ReviewRepository;
import com.pettoyou.server.review.repository.custom.ReviewCustomRepository;
import com.pettoyou.server.store.entity.Store;
import com.pettoyou.server.store.entity.enums.StoreType;
import com.pettoyou.server.store.repository.StoreRepository;
import com.querydsl.core.Tuple;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    StoreRepository storeRepository;
    ReviewRepository reviewRepository;
    ReviewCustomRepository reviewCustomRepository;
    PetRepository petRepository;

    public String registerReiview(Long storeId,Long petId,  Long userId, List<MultipartFile> reviewImgs, ReviewReqDto reviewReqDto){
        Store store = storeRepository.findById(storeId).orElseThrow(() -> new CustomException(CustomResponseStatus.STORE_NOT_FOUND));
        Pet pet  = petRepository.findById(petId).orElseThrow(() -> new CustomException(CustomResponseStatus.PET_NOT_FOUND));
        StoreType storeType = store.getStoreType();
        //병원 or 미용실
        Review reviewEntity = ReviewReqDto.toEntity(store, pet, userId, reviewReqDto, storeType);
        reviewRepository.save(reviewEntity);
        return reviewEntity.getReviewId().toString();
    }

    public Page<ReviewRespDto> getReview(Long storeId, Pageable pageable){
        Page<Tuple> reviewAndPet = reviewCustomRepository.findReviewsFetchJoinPetsByStoreId(storeId, pageable);
         List<ReviewRespDto> result = reviewAndPet.stream()
                 .map(ReviewRespDto::toDto).toList();

         return new PageImpl<>(result, reviewAndPet.getPageable(), reviewAndPet.getTotalElements());

    }

}
