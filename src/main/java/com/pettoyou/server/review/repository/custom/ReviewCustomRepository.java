package com.pettoyou.server.review.repository.custom;

import com.pettoyou.server.review.dto.ReviewReqDto;
import com.querydsl.core.Tuple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewCustomRepository {
    Page<Tuple>findReviewsFetchJoinPetsByStoreId(Long StoreId, Pageable pageable);

    long updatePinned(Long reviewId, Integer pinned);
}
