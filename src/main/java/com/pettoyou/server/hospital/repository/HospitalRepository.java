package com.pettoyou.server.hospital.repository;

import com.pettoyou.server.hospital.entity.Hospital;
import com.pettoyou.server.hospital.repository.custom.HospitalCustomRepository;
import com.pettoyou.server.store.interfaces.StoreInterface;
import io.lettuce.core.dynamic.annotation.Param;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface HospitalRepository extends JpaRepository<Hospital, Long>, HospitalCustomRepository {
  @EntityGraph(
    attributePaths = { "storePhotos" },
    type = EntityGraph.EntityGraphType.LOAD
  )
  Optional<Hospital> findDistinctHospitalByStoreId(Long hospitalId);

  //   @Query(
  //     """
  //      SELECT h, bh, sp FROM Hospital as h
  //      LEFT JOIN BusinessHour as bh
  //      LEFT JOIN StorePhoto as sp
  //      WHERE h.storeId = :hospitalId

  //   """
  //   )
  //   Optional<Hospital> findDistinctHospitalByStoreId(
  //     @Param("hospitalId") Long hospitalId
  //   );

  // 해당 반경 안에 있는 병원
  @Query(
    value = """
    SELECT h.storeName as hospitalName, h.storeId as storeId, h.thumbnailUrl as thumbnailUrl, b as businessHours, COUNT(r.reviewId) as reviewCount, AVG(r.rating) as rateAvg, ST_Distance_Sphere(ST_PointFromText(:point, 4326), h.address.point) as distance 
    FROM Hospital as h 
    LEFT JOIN h.businessHours as b on b.dayOfWeek=:dayOfWeek 
    LEFT JOIN h.reviews as r  
    WHERE ST_Contains(ST_BUFFER(ST_PointFromText(:point, 4326), :radius), h.address.point)
    GROUP BY h,r,b
    ORDER BY distance asc
"""

  )
  Page<StoreInterface> findHospitals(
          Pageable pageable,
    @Param("point") String point,
    @Param("radius") Integer radius,
    @Param("dayOfWeek") Integer dayOfWeek
  );

  // 해당 반경 안에 있는 병원 + OPEN 중인 것만 단, BreakTime 인 병원 포함 ** 재사용 가능하게 변경 필요 **
  @Query(
    """
    SELECT h.storeName as hospitalName, h.storeId as storeId, h.thumbnailUrl as thumbnailUrl, b as businessHours, COUNT(r.reviewId) as reviewCount, AVG(r.rating) as rateAvg, ST_Distance_Sphere(ST_PointFromText(:point, 4326), h.address.point) as distance 
    FROM Hospital as h 
    LEFT JOIN h.businessHours as b on b.dayOfWeek=:dayOfWeek 
    LEFT JOIN h.reviews as r  
    WHERE ST_Contains(ST_BUFFER(ST_PointFromText(:point, 4326), :radius), h.address.point) and :now between b.startTime AND b.endTime GROUP BY h,r,b ORDER BY distance asc
"""
  )
  Page<StoreInterface> findHospitalsOpen(
          Pageable pageable,
    @Param("point") String point,
    @Param("radius") Integer radius,
    @Param("dayOfWeek") Integer dayOfWeek,
    @Param("now") LocalTime now
  );
}
