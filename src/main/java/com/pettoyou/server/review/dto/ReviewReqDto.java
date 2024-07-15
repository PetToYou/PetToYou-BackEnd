package com.pettoyou.server.review.dto;


import com.pettoyou.server.pet.entity.Pet;
import com.pettoyou.server.review.entity.Review;
import com.pettoyou.server.store.entity.Store;
import com.pettoyou.server.store.entity.enums.StoreType;

public record ReviewReqDto(String treatmentType,
                           String treatment,
                           Integer price,
                           Integer rating,
                           String content
)
{
    public static Review toEntity(Store store, Pet pet, Long userId, ReviewReqDto reviewReqDto, StoreType storeType)
    {
        return Review.builder()
                .memberId(userId)
                .store(store)
                .pet(pet)
                .storeType(storeType)
                .rating(reviewReqDto.rating)
                .content(reviewReqDto.content)
                .treatmentType(reviewReqDto.treatmentType)
                .treatment(reviewReqDto.treatment)
                .price(reviewReqDto.price)
                .build();
//null 값 어카지 ?
    }

    public static Review toEntity(ReviewReqDto reviewReqDto){
        return Review.builder()
                .rating(reviewReqDto.rating)
                .content(reviewReqDto.content)
                .treatmentType(reviewReqDto.treatmentType)
                .treatment(reviewReqDto.treatment)
                .price(reviewReqDto.price)
                .build();
    }

}
//{
//	진료항목 종류 treatment_type
//	진료항목 treatment
//	치료비 price
//	평점 rate
//	내용 content
//}