package com.pettoyou.server.hospital.repository;

import com.pettoyou.server.hospital.dto.HospitalDto;
import com.pettoyou.server.hospital.interfaces.ContainInterface;
import com.pettoyou.server.hospital.entity.Hospital;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface HospitalRepository extends JpaRepository<Hospital, Long> {

    @EntityGraph(attributePaths = {"storePhotos"},  type = EntityGraph.EntityGraphType.LOAD)
    Optional<Hospital> findDistinctHospitalFetchJoinByStoreId(Long hospitalId);



@Query("""
    SELECT h.storeName as hospitalName, h.storeId as storeId, h.thumbnailUrl as thumbnailUrl, b as businessHours, COUNT(r.reviewId) as reviewCount, AVG(r.rating) as rateAvg, ST_Distance_Sphere(ST_PointFromText(:point, 4326), h.address.point) as distance 
    FROM Hospital as h 
    LEFT JOIN h.businessHours as b on b.dayOfWeek=:dayOfWeek 
    LEFT JOIN h.reviews as r  
    WHERE ST_CONTAINS(ST_BUFFER(ST_PointFromText(:point, 4326), :radius), h.address.point) GROUP BY h, r, b ORDER BY distance asc
""")
    List<ContainInterface> findHospitalsContain(@Param("point") String point, @Param("radius") Integer radius, @Param("dayOfWeek") Integer dayOfWeek);
}
