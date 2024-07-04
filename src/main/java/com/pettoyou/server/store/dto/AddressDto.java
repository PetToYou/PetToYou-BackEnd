package com.pettoyou.server.store.dto;

import com.pettoyou.server.store.entity.Address;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;
import org.springframework.beans.factory.annotation.Value;

/**
 * DTO for {@link com.pettoyou.server.store.entity.Address}
 */


@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Slf4j
public class AddressDto  {
    @NotNull
    private String zipCode;
    @NotNull
    private String sido;
    @NotNull
    private String sigungu;

    private String eupmyun;
    @NotNull
    private String doro;

    private String addressDetail;

    @NotNull
    private double latitude;

    @NotNull
    private double longitude;



    public static Address toEntity(AddressDto addressDto)  {


       String pointWKT = (String.format("POINT(%s %s)", addressDto.longitude, addressDto.latitude));
        Point point = null;
        log.info(pointWKT);
        try {
            point = (Point) new WKTReader().read(pointWKT);
            point.setSRID(4326);
            //Exception 만드릭
            log.info(point.toString());
        }
        catch (ParseException e)     {
            System.out.println("Parsing Error in Point"  + e.getMessage());
        }



        return Address.builder()
                .sido(addressDto.sido)
                .sigungu(addressDto.sigungu)
                .eupmyun(addressDto.eupmyun)
                .doro(addressDto.doro)
                .addressDetail(addressDto.addressDetail)
                .zipCode(addressDto.zipCode)
                .point(point)
                .build();

    }
}