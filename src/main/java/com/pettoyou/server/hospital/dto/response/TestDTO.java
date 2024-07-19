package com.pettoyou.server.hospital.dto.response;

import com.pettoyou.server.hospital.entity.Hospital;
import com.pettoyou.server.review.entity.Review;
import com.pettoyou.server.store.dto.response.TagInfo;
import lombok.Builder;

@Builder
public record TestDTO(
        Long storeId,
        String storeName,
        String thumbnailUrl,
        Times time,
        Long reviewCount,
        Double ratingAvg,
        String distance,
        TagInfo tags
) {

    public static TestDTO of(Hospital hospital,double distance,  int dayOfWeek) {
        return TestDTO.builder()
                .storeId(hospital.getStoreId())
                .storeName(hospital.getStoreName())
                .thumbnailUrl(hospital.getThumbnail() == null ? "test.jpg" : hospital.getThumbnail().getPhotoUrl())
                .time(Times.of(hospital.getBusinessHours(), dayOfWeek))
                .reviewCount(hospital.getReviews() == null ? 5L : hospital.getReviews().size())
                .ratingAvg(hospital.getReviews().isEmpty() ? 4.5 : Review.getRatingAvg(hospital.getReviews()))
                .distance(distanceFormatting(distance))
                .tags(TagInfo.from(hospital.getTags()))
                .build();
    }

    private static String distanceFormatting(double distance) {
        return String.format("0.1f", distance / 1000.0 );
    }

}
