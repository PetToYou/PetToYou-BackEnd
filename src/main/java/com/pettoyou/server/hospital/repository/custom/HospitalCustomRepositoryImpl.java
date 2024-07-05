package com.pettoyou.server.hospital.repository.custom;

import com.pettoyou.server.hospital.dto.request.HospitalQueryCond;
import com.pettoyou.server.hospital.entity.enums.HospitalTagType;
import com.pettoyou.server.store.dto.response.StoreQueryInfo;
import com.pettoyou.server.store.dto.response.StoreQueryTotalInfo;
import com.pettoyou.server.store.dto.response.TagInfo;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.pettoyou.server.hospital.entity.QHospital.hospital;
import static com.pettoyou.server.hospital.entity.QHospitalTag.hospitalTag;
import static com.pettoyou.server.hospital.entity.QTagMapper.tagMapper;
import static com.pettoyou.server.review.entity.QReview.review;
import static com.pettoyou.server.store.entity.QBusinessHour.businessHour;

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

        BooleanBuilder distanceBuilder = new BooleanBuilder();
        // 동적 쿼리를 위한 BooleanBuilder 객체 생성
        distanceBuilder.and(
                Expressions.booleanTemplate(
                        "ST_Contains(ST_Buffer(ST_PointFromText({0}, 4326), {1}), {2})",
                        point, queryCond.radius(), hospital.address.point
                )
        );

        BooleanBuilder tagBuilder = new BooleanBuilder();
        if (queryCond.businessHourCond() != null) {
            log.info("businessHourCond : {}", queryCond.businessHourCond());
            tagBuilder
                    .and(hospitalTag.tagType.eq(HospitalTagType.BUSINESSHOUR)
                            .and(hospitalTag.tagContent.eq(queryCond.businessHourCond())));
        }
        if (queryCond.specialitiesCond() != null) {
            log.info("specialitiesCond : {}", queryCond.specialitiesCond());
            tagBuilder.and(hospitalTag.tagType.eq(HospitalTagType.SPECIALITIES).and(hospitalTag.tagContent.eq(queryCond.specialitiesCond())));
        }
        if (queryCond.emergencyCond().equals("EMERGENCY")) {
            log.info("emergencyCond : {}", queryCond.emergencyCond());

            tagBuilder.and(hospitalTag.tagType.eq(HospitalTagType.EMERGENCY).and(hospitalTag.tagContent.eq(queryCond.emergencyCond())));
        }

        BooleanBuilder timeBuilder = new BooleanBuilder();
        if (queryCond.openCond().equals("OPEN")) {
            log.info("openCond : {}", queryCond.openCond());

            timeBuilder
                    .and(businessHour.startTime.isNotNull()
                            .and(businessHour.endTime.isNotNull()));
            timeBuilder
                    .and(businessHour.startTime.loe(Time.valueOf(now))
                    .and(businessHour.endTime.goe(Time.valueOf(now))));
        }

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
                .where(distanceBuilder.and(tagBuilder).and(timeBuilder))
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

}
