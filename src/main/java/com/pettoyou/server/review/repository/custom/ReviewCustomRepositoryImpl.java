package com.pettoyou.server.review.repository.custom;

import com.pettoyou.server.pet.entity.QPet;
import com.pettoyou.server.review.entity.QReview;
import com.pettoyou.server.review.entity.Review;
import com.pettoyou.server.util.QueryDslUtil;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Stream;

@Repository
public class ReviewCustomRepositoryImpl implements ReviewCustomRepository {


    private final JPAQueryFactory jpaQueryFactory;
    QueryDslUtil queryDslUtil;
    public ReviewCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public Page<Tuple> findReviewsFetchJoinPetsByStoreId(Long StoreId, Pageable pageable)
    {
        QReview review = QReview.review;
        QPet pet = QPet.pet;
        //기본 order (상단 고정 기능)
        OrderSpecifier<?> pinnedOrder = review.pinned.desc();
        //Pageable.Sort
        OrderSpecifier<?>[] pageableOrder = queryDslUtil.getOrderSpecifiers(pageable.getSort(), Review.class).stream().toArray(OrderSpecifier[]::new);
        //결합된 Sort 조건.
        OrderSpecifier<?>[] combinedOrder = Stream.concat(Stream.of(pinnedOrder), Stream.of(pageableOrder))
                .toArray(OrderSpecifier[]::new);

        QueryResults<Tuple> results = jpaQueryFactory.select(review, review.pet)
                .from(review)
                .join(review.pet, pet).fetchJoin()
                .where(review.store.storeId.eq(StoreId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(combinedOrder)
                .fetchResults();
        //havgin, groupby에서는 deprecated 함.추후 변경.
        //여기선 간단한 코드라 그냥 샤용할게요.
        List<Tuple> content = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(content, pageable, total);
    }

    public long updatePinned(Long reviewId, Integer pinned)
    {
        QReview review = QReview.review;
        long result = jpaQueryFactory.update(review)
                .set(review.pinned, pinned)
                .where(review.reviewId.eq(reviewId))
                .execute();
        return result;

    }
}

