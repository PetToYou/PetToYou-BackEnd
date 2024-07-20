package com.pettoyou.server.store.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pettoyou.server.store.entity.Address;
import lombok.Builder;

/**
 * DTO for {@link com.pettoyou.server.store.entity.Address}
 */
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public record AddressDto(String sido,
                         String sigungu,
                         String eupmyun)
        {

                public static AddressDto toDto(Address address){
                        return AddressDto.builder()
                                .sido(address.getSido())
                                .eupmyun(address.getEupmyun())
                                .sigungu(address.getSigungu())
                                .build();
                }
}