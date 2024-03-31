package com.pettoyou.server.hospital.repository;

import com.pettoyou.server.hospital.interfaces.ContainInterface;
import com.pettoyou.server.hospital.entity.Hospital;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface HospitalRepository extends JpaRepository<Hospital, Long> {


//    @Query("""
//    SELECT h.storeName as hospitalName, h.storeId as storeId, h.thumbnailUrl as thumbnailUrl,  ST_Distance_Sphere(ST_PointFromText(:point, 4326), h.address.point) as distance FROM Hospital as h WHERE ST_CONTAINS(ST_BUFFER(ST_PointFromText(:point, 4326), :radius), h.address.point) ORDER BY distance asc
//""")
//    List<ContainInterface> findHospitalsContain(@Param("point") String point, @Param("radius") Integer radius);
//}


@Query("""
    SELECT h.storeName as hospitalName, h.storeId as storeId, h.thumbnailUrl as thumbnailUrl, b as businessHours,  ST_Distance_Sphere(ST_PointFromText(:point, 4326), h.address.point) as distance FROM Hospital as h LEFT JOIN h.businessHours as b on b.dayOfWeek=:dayOfWeek  WHERE ST_CONTAINS(ST_BUFFER(ST_PointFromText(:point, 4326), :radius), h.address.point) ORDER BY distance asc
""")
    List<ContainInterface> findHospitalsContain(@Param("point") String point, @Param("radius") Integer radius, @Param("dayOfWeek") Integer dayOfWeek);
}
