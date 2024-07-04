package com.pettoyou.server.hospital.repository.custom;

import com.pettoyou.server.store.dto.response.StoreQueryInfo;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.time.LocalTime;
import java.util.List;

import static com.pettoyou.server.hospital.entity.QHospital.hospital;
import static com.pettoyou.server.review.entity.QReview.review;
import static com.pettoyou.server.store.entity.QBusinessHour.businessHour;

@Repository
@Slf4j
public class HospitalCustomRepositoryImpl implements HospitalCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public HospitalCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    // Todo : 아래 두개의 메서드는 오픈의 유무만 가리기 때문에 이를 하나의 메서드로 통합할 수 있을것으로 보이며 추후 적용 필요

    @Override
    public Page<StoreQueryInfo> findHospitalsWithinRadius(Pageable pageable, int dayOfWeek, String point, int radius) {
        List<StoreQueryInfo> fetch = jpaQueryFactory
                .select(
                        Projections.constructor(
                                StoreQueryInfo.class,
                                hospital.storeId.as("storeId"),
                                hospital.storeName.as("storeName"),
                                hospital.thumbnailUrl.as("thumbnailUrl"),
                                businessHour.startTime.as("startTime"),
                                businessHour.endTime.as("endTime"),
                                businessHour.breakStartTime.as("breakStartTime"),
                                businessHour.breakEndTime.as("breakEndTime"),
                                review.reviewId.count().as("reviewCount"),
                                review.rating.avg().as("ratingAvg"),
//                                hospital.tags.as(""), // TODO
                                Expressions.stringTemplate(
                                        "ST_Distance_Sphere(ST_PointFromText({0}, 4326), {1})",
                                        point, hospital.address.point
                                ).castToNum(Double.class).as("distance")
                        )
                ).from(hospital)
                .leftJoin(hospital.businessHours, businessHour)
                .on(businessHour.dayOfWeek.eq(dayOfWeek))
                .leftJoin(hospital.reviews, review)
                .where(
                        Expressions.booleanTemplate(
                                "ST_Contains(ST_Buffer(ST_PointFromText({0}, 4326), {1}), {2})"
                                , point, radius, hospital.address.point
                        )
                )
                .groupBy(
                        hospital.storeId,
                        hospital.storeName,
                        businessHour.startTime,
                        businessHour.endTime,
                        businessHour.breakStartTime,
                        businessHour.breakEndTime
                )
                .orderBy(Expressions.stringTemplate(
                        "ST_Distance_Sphere(ST_PointFromText({0}, 4326), {1})",
                        point, hospital.address.point
                ).asc())
                .fetch();

        long total = fetch.size();

        return new PageImpl<>(fetch, pageable, total);
    }

    @Override
    public Page<StoreQueryInfo> findHospitalsWithinRadiusAndOpen(
            Pageable pageable,
            int dayOfWeek,
            String point,
            int radius,
            LocalTime now
    ) {
        log.info("now : {}", now);
        log.info("now Time : {}", Time.valueOf(now));
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(
                Expressions.booleanTemplate(
                        "ST_Contains(ST_Buffer(ST_PointFromText({0}, 4326), {1}), {2})",
                        point, radius, hospital.address.point
                )
        );
        // Todo : 현재는 null인 데이터가 있기 때문에 not null 조건을 추가해주었음. 추후 빼도됨
        builder.and(businessHour.startTime.isNotNull().and(businessHour.endTime.isNotNull()));
        builder.and(businessHour.startTime.loe(Time.valueOf(now)).and(businessHour.endTime.goe(Time.valueOf(now.minusHours(2)))));

        List<StoreQueryInfo> fetch = jpaQueryFactory
                .select(
                        Projections.constructor(
                                StoreQueryInfo.class,
                                hospital.storeId.as("storeId"),
                                hospital.storeName.as("storeName"),
                                hospital.thumbnailUrl.as("thumbnailUrl"),
                                businessHour.startTime.as("startTime"),
                                businessHour.endTime.as("endTime"),
                                businessHour.breakStartTime.as("breakStartTime"),
                                businessHour.breakEndTime.as("breakEndTime"),
                                review.reviewId.count().as("reviewCount"),
                                review.rating.avg().as("ratingAvg"),
                                Expressions.stringTemplate(
                                        "ST_Distance_Sphere(ST_PointFromText({0}, 4326), {1})",
                                        point, hospital.address.point
                                ).castToNum(Double.class).as("distance")
                        )
                ).from(hospital)
                .leftJoin(hospital.businessHours, businessHour)
                .on(businessHour.dayOfWeek.eq(dayOfWeek))
                .leftJoin(hospital.reviews, review)
                .where(
                        builder
                )
                .groupBy(
                        hospital.storeId,
                        hospital.storeName,
                        businessHour.startTime,
                        businessHour.endTime,
                        businessHour.breakStartTime,
                        businessHour.breakEndTime
                )
                .orderBy(Expressions.stringTemplate(
                        "ST_Distance_Sphere(ST_PointFromText({0}, 4326), {1})",
                        point, hospital.address.point
                ).asc())
                .fetch();

        long total = fetch.size();

        return new PageImpl<>(fetch, pageable, total);
    }
}
