package com.pettoyou.server.hospital.repository.custom;

import com.pettoyou.server.constant.enums.CustomResponseStatus;
import com.pettoyou.server.constant.exception.CustomException;
import com.pettoyou.server.hospital.dto.request.HospitalQueryCond;
import com.pettoyou.server.hospital.dto.request.HosptialSearchQueryInfo;
import com.pettoyou.server.hospital.dto.response.HospitalDetail;
import com.pettoyou.server.hospital.dto.response.HospitalDtoWithAddress;
import com.pettoyou.server.hospital.dto.response.HospitalDtoWithDistance;
import com.pettoyou.server.hospital.dto.response.Times;
import com.pettoyou.server.hospital.entity.Hospital;
import com.pettoyou.server.hospital.entity.HospitalTag;
import com.pettoyou.server.hospital.entity.QHospital;
import com.pettoyou.server.hospital.entity.TagMapper;
import com.pettoyou.server.review.entity.QReview;
import com.pettoyou.server.store.entity.BusinessHour;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.pettoyou.server.hospital.entity.QHospital.hospital;
import static com.pettoyou.server.hospital.entity.QTagMapper.tagMapper;
import static com.pettoyou.server.review.entity.QReview.*;
import static com.pettoyou.server.store.entity.QBusinessHour.businessHour;
import static com.pettoyou.server.store.entity.QRegistrationInfo.registrationInfo;

@Repository
@RequiredArgsConstructor
@Slf4j
public class HospitalCustomRepositoryImpl implements HospitalCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<HospitalDtoWithDistance> findHospitalOptimization(
            Pageable pageable,
            int dayOfWeek, String point, LocalTime now, HospitalQueryCond queryCond
    ) {
        NumberPath<Double> distanceAlias = Expressions.numberPath(Double.class, "distance");
        NumberPath<Long> reviewCount = Expressions.numberPath(Long.class, "reviewCount");
        NumberPath<Double> ratingAvg = Expressions.numberPath(Double.class, "ratingAvg");

        // 1. 필터 조건에 부합하는 병원 가져오기
        List<Tuple> hospitals = jpaQueryFactory
                .select(
                        hospital.storeId,
                        hospital.storeName,
                        hospital.thumbnail.photoUrl,
                        businessHour,
                        review.countDistinct().as(reviewCount),
                        review.rating.avg().as(ratingAvg),
                        Expressions.stringTemplate(
                                "ST_Distance_Sphere(ST_PointFromText({0}, 4326), {1})",
                                point, hospital.address.point
                        ).as("distance"))
                .from(hospital)
                .leftJoin(hospital.businessHours, businessHour)
                .on(businessHour.dayOfWeek.eq(dayOfWeek))
                .leftJoin(hospital.reviews, review)
                .where(
                        inDistance(point, queryCond.radius()),
                        hospitalTagsEqSubQuery(queryCond.tagIdList()),
                        openHospitalSubQuery(queryCond.openCond(), Time.valueOf(now), dayOfWeek)
                )
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .orderBy(distanceAlias.asc())
                .groupBy(
                        hospital,
                        businessHour
                )
                .fetch();

        // 2. 각 병원에서 가지고있는 HospitalTag를 Map에 담기
        Map<Long, List<HospitalTag>> hospitalTagMap = jpaQueryFactory
                .select(tagMapper)
                .from(tagMapper)
                .where(
                        tagMapper.hospital.storeId.in(
                                hospitals.stream()
                                        .map(h -> h.get(hospital.storeId))
                                        .toList()
                        )
                )
                .fetch()
                .stream()
                .collect(Collectors.groupingBy(
                        tm -> tm.getHospital().getStoreId(),
                        Collectors.mapping(TagMapper::getHospitalTag, Collectors.toList())
                ));

        // 3. 페이징을 위한 조회된 병원의 전체 개수 구하는 쿼리
        Long countQuery = jpaQueryFactory
                .select(hospital.count())
                .from(hospital)
                .where(
                        inDistance(point, queryCond.radius()),
                        hospitalTagsEqSubQuery(queryCond.tagIdList()),
                        openHospitalSubQuery(queryCond.openCond(), Time.valueOf(now), dayOfWeek)
                )
                .fetchOne();

        if (countQuery == null) throw new CustomException(CustomResponseStatus.STORE_NOT_FOUND);

        List<HospitalDtoWithDistance> result = hospitals.stream()
                .map(t -> {
                    Long storeId = t.get(hospital.storeId);
                    String storeName = t.get(hospital.storeName);
                    String thumbnailUrl = t.get(hospital.thumbnail.photoUrl);
                    Long reviewCnt = t.get(reviewCount);
                    Double ratingAverage = t.get(ratingAvg);
                    Double distance = t.get(distanceAlias);
                    BusinessHour storeBusinessHour = t.get(businessHour);

                    return HospitalDtoWithDistance.of(
                            storeId,
                            storeName,
                            thumbnailUrl,
                            reviewCnt,
                            ratingAverage,
                            Times.of(storeBusinessHour),
                            hospitalTagMap.get(storeId),
                            distance
                    );
                })
                .toList();

        log.info(pageable.toString(), "page");
        return new PageImpl<>(result, pageable, countQuery);
    }

    @Override
    public Page<HospitalDtoWithAddress> findHospitalBySearch(Pageable pageable, HosptialSearchQueryInfo queryInfo, Integer dayOfWeek) {
        QHospital hospital = QHospital.hospital;
        List<HospitalDtoWithAddress> content = jpaQueryFactory
                .select(Projections.constructor(HospitalDtoWithAddress.class,
                        hospital.storeId, hospital.storeName,
                        hospital.thumbnail.photoUrl.as("thumbnailUrl"), hospital.address,
                        businessHour)
                )
                .from(hospital)
                .leftJoin(hospital.businessHours, businessHour)
                .on(businessHour.dayOfWeek.eq(dayOfWeek))
                .where(hospital.storeName.contains(queryInfo.storeName()))
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();

        Long total = jpaQueryFactory.
                select(hospital.count())
                .from(hospital)
                .where(hospital.storeName.contains(queryInfo.storeName()))
                .fetchOne();

        if (total == null) {
            throw new CustomException(CustomResponseStatus.STORE_NOT_FOUND);
        }
// query Projection 문서 참고.
//        List<HospitalDtoWithAddress> results = content.stream().map(tuple -> HospitalDtoWithAddress.of(
//                        tuple.get(hospital.storeId),
//                        tuple.get(hospital.storeName),
//                        tuple.get(hospital.thumbnail.photoUrl),
//                        tuple.get(hospital.address),
//                        tuple.get(businessHour))).collect(Collectors.toList());


        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public HospitalDetail findHospitalDetailById(Long hospitalId) {
        Hospital findHospital = jpaQueryFactory
                .selectFrom(hospital)
                .leftJoin(hospital.registrationInfo, registrationInfo).fetchJoin()
                .where(hospital.storeId.eq(hospitalId))
                .fetchOne();

        if (findHospital == null) {
            throw new CustomException(CustomResponseStatus.HOSPITAL_NOT_FOUND);
        }

        return HospitalDetail.from(findHospital);
    }

    private BooleanExpression hospitalTagsEqSubQuery(List<Long> tagsCond) {
        // BusinessHour, Specialities, Emergency 모두 여기서 처리됨.
        return tagsCond != null
                ? hospital.storeId.in(
                JPAExpressions
                        .select(tagMapper.hospital.storeId)
                        .from(tagMapper)
                        .where(tagMapper.hospitalTag.hospitalTagId.in(tagsCond)))
                : null;
    }

    private BooleanExpression openHospitalSubQuery(String openCond, Time now, int dayOfWeek) {
        return openCond != null
                ? hospital.storeId.in(
                JPAExpressions
                        .select(businessHour.store.storeId)
                        .from(businessHour)
                        .where(
                                businessHour.dayOfWeek.eq(dayOfWeek)
                                        .and(businessHour.startTime.isNotNull())
                                        .and(businessHour.endTime.isNotNull())
                                        .and(businessHour.startTime.loe(now))
                                        .and(businessHour.endTime.goe(now))
                        )
        )
                : null;
    }

    private BooleanExpression inDistance(String point, Integer radius) {
        return radius != null
                ? Expressions.booleanTemplate(
                "ST_Contains(ST_Buffer(ST_PointFromText({0}, 4326), {1}), {2})",
                point, radius, hospital.address.point)
                : null;
    }
}
