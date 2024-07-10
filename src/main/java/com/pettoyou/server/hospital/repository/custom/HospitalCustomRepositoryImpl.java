package com.pettoyou.server.hospital.repository.custom;

import com.pettoyou.server.hospital.dto.request.HospitalQueryCond;
import com.pettoyou.server.hospital.dto.response.TestDTO;
import com.pettoyou.server.hospital.dto.response.Times;
import com.pettoyou.server.hospital.entity.Hospital;
import com.pettoyou.server.hospital.entity.enums.HospitalTagType;
import com.pettoyou.server.review.entity.Review;
import com.pettoyou.server.store.dto.response.StoreQueryInfo;
import com.pettoyou.server.store.dto.response.StoreQueryTotalInfo;
import com.pettoyou.server.store.dto.response.TagInfo;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.pettoyou.server.hospital.entity.QHospital.hospital;
import static com.pettoyou.server.hospital.entity.QHospitalTag.hospitalTag;
import static com.pettoyou.server.hospital.entity.QTagMapper.tagMapper;
import static com.pettoyou.server.review.entity.QReview.review;
import static com.pettoyou.server.store.entity.QBusinessHour.businessHour;
import static com.pettoyou.server.store.entity.QRegistrationInfo.registrationInfo;

@Repository
@Slf4j
public class HospitalCustomRepositoryImpl implements HospitalCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public HospitalCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }


    @Override
    public Page<StoreQueryTotalInfo> findHospitalsWithinRadius(
            Pageable pageable,
            int dayOfWeek,
            String point,
            LocalTime now,
            HospitalQueryCond queryCond
    ) {
        List<StoreQueryInfo> hospitalInfoList = jpaQueryFactory
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
                .leftJoin(hospital.tags, tagMapper)
                .leftJoin(tagMapper.hospitalTag, hospitalTag)
                .where(
                        inDistance(point, queryCond.radius()),
                        businessHourEq(queryCond.businessHourCond()),
                        specialitiesEq(queryCond.specialitiesCond()),
                        emergencyEq(queryCond.emergencyCond()),
                        isOpen(queryCond.openCond(), Time.valueOf(now.minusHours(4)))
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
        log.info("size : {}", hospitalInfoList.size());

        Map<Long, TagInfo> tagsMap = new ConcurrentHashMap<>();
        for (StoreQueryInfo hospitalInfo : hospitalInfoList) {
            Long storeId = hospitalInfo.storeId();
            tagsMap.putIfAbsent(storeId, new TagInfo(new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), "NO"));

            List<Tuple> tagInfoTuples = jpaQueryFactory
                    .select(
                            tagMapper.hospital.storeId,
                            hospitalTag.tagType,
                            hospitalTag.tagContent
                    )
                    .from(tagMapper)
                    .leftJoin(tagMapper.hospitalTag, hospitalTag)
                    .where(tagMapper.hospital.storeId.eq(storeId))
                    .fetch();

            for (Tuple tagInfoTuple : tagInfoTuples) {
                HospitalTagType tagType = tagInfoTuple.get(hospitalTag.tagType);
                String tagContent = tagInfoTuple.get(hospitalTag.tagContent);

                TagInfo currentTagInfo = tagsMap.get(storeId);
                if (tagType.equals(HospitalTagType.SERVICE)) currentTagInfo.services().add(tagContent);
                else if (tagType.equals(HospitalTagType.BUSINESSHOUR)) currentTagInfo.businessHours().add(tagContent);
                else if (tagType.equals(HospitalTagType.SPECIALITIES)) currentTagInfo.specialists().add(tagContent);
                else {
                    // record는 불변객체기 때문에 객체를 수정할시에 인스턴스를 새로 만들어줘야함.
                    tagsMap.put(storeId, new TagInfo(
                            currentTagInfo.services(),
                            currentTagInfo.businessHours(),
                            currentTagInfo.specialists(),
                            tagContent
                    ));
                }
            }
        }

        List<StoreQueryTotalInfo> result = hospitalInfoList.stream()
                .map(hospitalInfo -> {
                    TagInfo tags = tagsMap.getOrDefault(hospitalInfo.storeId(), new TagInfo(new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), "NO"));
                    return new StoreQueryTotalInfo(
                            hospitalInfo.storeId(),
                            hospitalInfo.storeName(),
                            hospitalInfo.thumbnailUrl(),
                            hospitalInfo.startTime(),
                            hospitalInfo.endTime(),
                            hospitalInfo.breakStartTime(),
                            hospitalInfo.breakEndTime(),
                            hospitalInfo.reviewCount(),
                            hospitalInfo.ratingAvg(),
                            hospitalInfo.distance(),
                            tags
                    );
                })
                .toList();

        long total = result.size();

        return new PageImpl<>(result, pageable, total);
    }

    @Override
    public Page<TestDTO> findHospitalOptimization(
            Pageable pageable,
            int dayOfWeek, String point, LocalTime now, HospitalQueryCond queryCond) {
        // Todo : 동적 쿼리 적용해야하는데.. 답변이 달리면 진행할 예정!
        log.info("=============================================");
        log.info("테스트 시작~!~!~!~!~!~!~!~!~~!~!~!~!~!");
        log.info("필터링 정보 : {}", queryCond);
        NumberPath<Double> distanceAlias = Expressions.numberPath(Double.class, "distance");


        // 일단 반경 내의 병원 정보를 모두 가져옴.
        List<Tuple> hospitals = jpaQueryFactory
                .select(
                        hospital,
                        Expressions.stringTemplate(
                                "ST_Distance_Sphere(ST_PointFromText({0}, 4326), {1})",
                                point, hospital.address.point
                        ).castToNum(Double.class).as(distanceAlias)
                ).from(hospital)
                .leftJoin(hospital.registrationInfo, registrationInfo).fetchJoin()
                .where(inDistance(point, queryCond.radius()))
                .fetch();

//        // 병원 돌면서 DTO 채우기
        List<TestDTO> result = new ArrayList<>();
        for (Tuple tuple : hospitals) {
            Hospital hospital1 = tuple.get(hospital);
            Double distance = tuple.get(distanceAlias);
            log.info("Hospital: " + hospital1 + ", Distance: " + distance);

            result.add(TestDTO.builder()
                    .storeId(hospital1.getStoreId())
                    .storeName(hospital1.getStoreName())
                    .thumbnailUrl(hospital1.getThumbnailUrl())
                    .time(Times.of(hospital1.getBusinessHours(), dayOfWeek))
                    .reviewCount((long) hospital1.getReviews().size())
                    .ratingAvg(Review.getRatingAvg(hospital1.getReviews()))
                    .distance(formatDistance(distance))
                    .tags(TagInfo.from(hospital1.getTags()))
                    .build());
        }

        log.info("size : {}", result.size());

        for (TestDTO testDTO : result) {
            log.info("{}", testDTO);
        }

        // Page 객체로 반환
        return new PageImpl<>(result, pageable, result.size());
    }

    private BooleanExpression businessHourEq(String businessHourCond) {
        return businessHourCond != null ? hospitalTag.tagType.eq(HospitalTagType.BUSINESSHOUR).and(hospitalTag.tagContent.eq(businessHourCond)) : null;
    }

    private BooleanExpression specialitiesEq(String specialitiesCond) {
        return specialitiesCond != null ? hospitalTag.tagType.eq(HospitalTagType.SPECIALITIES).and(hospitalTag.tagContent.eq(specialitiesCond)) : null;
    }

    private BooleanExpression emergencyEq(String emergencyCond) {
        return emergencyCond.equals("EMERGENCY") ? hospitalTag.tagType.eq(HospitalTagType.EMERGENCY).and(hospitalTag.tagContent.eq(emergencyCond)) : null;
    }

    private BooleanExpression inDistance(String point, Integer radius) {
        return radius != null
                ?
                Expressions.booleanTemplate(
                        "ST_Contains(ST_Buffer(ST_PointFromText({0}, 4326), {1}), {2})",
                        point, radius, hospital.address.point
                ) : null;
    }

    private BooleanExpression isOpen(String isOpen, Time now) {
        return isOpen.equals("OPEN")
                ?
                businessHour.startTime.isNotNull().and(businessHour.endTime.isNotNull())
                        .and(businessHour.startTime.loe(now)).and(businessHour.endTime.goe(now))
                :
                null;
    }

    private double formatDistance(double distance) {
        return Math.round((distance / 1000.0) * 10) / 10.0;
    }

}
