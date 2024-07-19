package com.pettoyou.server.hospital.repository.custom;

import com.pettoyou.server.constant.enums.CustomResponseStatus;
import com.pettoyou.server.constant.exception.CustomException;
import com.pettoyou.server.hospital.dto.request.HospitalQueryCond;
import com.pettoyou.server.hospital.dto.response.HospitalDetail;
import com.pettoyou.server.hospital.dto.response.TestDTO;
import com.pettoyou.server.hospital.entity.Hospital;
import com.querydsl.core.Tuple;
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
    public Page<TestDTO> findHospitalOptimization(
            Pageable pageable,
            int dayOfWeek, String point, LocalTime now, HospitalQueryCond queryCond
    ) {
        NumberPath<Double> distanceAlias = Expressions.numberPath(Double.class, "distance");
        // 반경 내의 병원 + 태그 필터 정보들
        List<Tuple> hospitals = jpaQueryFactory
                .select(
                        hospital,
                        Expressions.stringTemplate(
                                "ST_Distance_Sphere(ST_PointFromText({0}, 4326), {1})",
                                point, hospital.address.point
                        ).castToNum(Double.class).as(distanceAlias))
                .from(hospital)
                .where(
                        inDistance(point, queryCond.radius()),
                        hospitalTagsEqSubQuery(queryCond.tagIdList()),
                        openHospitalSubQuery(queryCond.openCond(), Time.valueOf(now), dayOfWeek)
                )
                .orderBy(distanceAlias.asc())
                .fetch();

        log.info("hospital size : {}", hospitals.size());

        // 조회되는 병원이 없을 경우에 예외처리
        if (hospitals.isEmpty()) throw new CustomException(CustomResponseStatus.HOSPITAL_NOT_FOUND);

        // 병원 돌면서 DTO 생성
        List<TestDTO> result = hospitals.stream()
                .map(t -> {
                    Hospital matchingHospital = t.get(hospital);
                    Double distance = t.get(distanceAlias);
                    log.info("Hospital: " + matchingHospital + ", Distance: " + distance);
                    return TestDTO.of(matchingHospital, distance, dayOfWeek);
                })
                .toList();
        return new PageImpl<>(result, pageable, result.size());
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
                        .leftJoin(tagMapper.hospital, hospital)
                        .leftJoin(tagMapper.hospitalTag, hospitalTag)
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
