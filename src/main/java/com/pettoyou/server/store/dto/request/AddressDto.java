package com.pettoyou.server.store.dto.request;

import com.pettoyou.server.constant.enums.CustomResponseStatus;
import com.pettoyou.server.constant.exception.CustomException;
import com.pettoyou.server.store.entity.Address;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;

/**
 * DTO for {@link com.pettoyou.server.store.entity.Address}
 */

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Slf4j
public class AddressDto {
    @NotNull
    @Pattern(regexp = "^\\d{3}-\\d{2}$")
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

    public static Address toEntity(AddressDto addressDto) {

        if (addressDto.longitude < 124 || addressDto.longitude > 134) {
            throw new IllegalArgumentException("경도를 정확하게 입력해주세요 : 124~134");
        }
        if (addressDto.latitude < 34 || addressDto.latitude > 44) {
            throw new IllegalArgumentException("위도를 정확하게 입력해주세요 34~44");
        }

        String pointWKT = (String.format("POINT(%s %s)", addressDto.longitude, addressDto.latitude));
        Point point = null;
        log.info(pointWKT);


        try {
            point = (Point) new WKTReader().read(pointWKT);
            point.setSRID(4326);
            log.info(point.toString());
        } catch (ParseException e) {
            throw new CustomException(CustomResponseStatus.POINT_PARSING_ERROR);
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