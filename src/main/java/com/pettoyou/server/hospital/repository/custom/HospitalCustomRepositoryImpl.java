package com.pettoyou.server.hospital.repository.custom;

import com.pettoyou.server.constant.enums.CustomResponseStatus;
import com.pettoyou.server.constant.exception.CustomException;
import com.pettoyou.server.hospital.dto.request.HospitalQueryCond;
import com.pettoyou.server.hospital.dto.request.HosptialSearchQueryInfo;
import com.pettoyou.server.hospital.dto.response.HospitalDetail;
import com.pettoyou.server.hospital.dto.response.HospitalDtoWithAddress;
import com.pettoyou.server.hospital.dto.response.HospitalDtoWithDistance;
import com.pettoyou.server.hospital.entity.Hospital;
import com.pettoyou.server.hospital.entity.QHospital;
import com.pettoyou.server.review.entity.QReview;
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
import java.util.stream.Collectors;

import static com.pettoyou.server.hospital.entity.QHospital.hospital;
import static com.pettoyou.server.hospital.entity.QHospitalTag.hospitalTag;
import static com.pettoyou.server.hospital.entity.QTagMapper.tagMapper;
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
        QReview review = QReview.review;
        NumberPath<Double> distanceAlias = Expressions.numberPath(Double.class, "distance");
        List<Tuple> hospitals = jpaQueryFactory
                .select(
                        hospital,
                        review.countDistinct(), review.rating.avg(),
                        Expressions.stringTemplate(
                                "ST_Distance_Sphere(ST_PointFromText({0}, 4326), {1})",
                                point, hospital.address.point
                        ).as("distance"))
//                .castToNum(Double.class).as(distanceAlias)
                .from(hospital)
                .leftJoin(hospital.reviews, review)
                .where(
                        inDistance(point, queryCond.radius()),
                        hospitalTagsEqSubQuery(queryCond.tagIdList()),
                        openHospitalSubQuery(queryCond.openCond(), Time.valueOf(now), dayOfWeek)
                )
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .orderBy(distanceAlias.asc())
                .groupBy(hospital.storeId)
                .fetch();

        Long countQuery = jpaQueryFactory
                .select(
                        hospital.count())
                .from(hospital)
                .where(
                        inDistance(point, queryCond.radius()),
                        hospitalTagsEqSubQuery(queryCond.tagIdList()),
                        openHospitalSubQuery(queryCond.openCond(), Time.valueOf(now), dayOfWeek)
                )
                .fetchOne();
        if (countQuery == null) {
            throw new CustomException(CustomResponseStatus.STORE_NOT_FOUND);
        }
        ;

        log.info("hospital size : {}", hospitals.size());
        // 조회되는 병원이 없을 경우에 예외처리
        if (hospitals.isEmpty()) throw new CustomException(CustomResponseStatus.HOSPITAL_NOT_FOUND);

        List<HospitalDtoWithDistance> result = hospitals.stream()
                .map(t -> {
                    Hospital matchingHospital = t.get(hospital);
                    Double distance = t.get(distanceAlias);
                    log.info("Hospital: " + matchingHospital + ", Distance: " + distance);

                    return HospitalDtoWithDistance.of(matchingHospital, distance, dayOfWeek);
                })
                .toList();
        log.info(countQuery.toString());
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
                .where(hospital.storeName.like(queryInfo.storeName()))
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
//                        .leftJoin(tagMapper.hospital, hospital)
//                        .leftJoin(tagMapper.hospitalTag, hospitalTag)
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
