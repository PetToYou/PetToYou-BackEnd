package com.pettoyou.server.hospital.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pettoyou.server.hospital.dto.HospitalTagDto;
import com.pettoyou.server.hospital.entity.HospitalTag;
import com.pettoyou.server.store.dto.response.AddressDto;
import com.pettoyou.server.store.entity.Address;
import com.pettoyou.server.store.entity.BusinessHour;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.util.List;

/**
 * DTO for {@link com.pettoyou.server.hospital.entity.Hospital}
 */
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public record HospitalDtoWithAddress(
        @NotNull Long storeId,
        @NotNull @Size(min = 2) String storeName,
        String thumbnailUrl,
        AddressDto addressDto,
        //response/dto
        Times time,
        HospitalTagDto tags
) {
    public static HospitalDtoWithAddress of(
            Long storeId,
            String storeName,
            String thumbnailUrl,
            Address address,
            BusinessHour businessHour
           , List<HospitalTag> tags
    ) {

        return HospitalDtoWithAddress.builder()
                .storeId(storeId)
                .storeName(storeName)
                .thumbnailUrl(thumbnailUrl)
                .addressDto(AddressDto.toDto(address))
                .time(businessHour != null ? Times.of(businessHour) : null)
                .tags(HospitalTagDto.toDto(tags))
                .build();
    }

    // *********null 수정 필요 {@link com.pettoyou.server.Times.java} *************//

    //필드주입방식
//             public static HospitalDtoWithAddress of(Long storeId, String storeName, String photoUrl, Address address, BusinessHour businessHour)
//             {
//                 return HospitalDtoWithAddress.builder()
//                         .storeId(storeId)
//                         .storeName(storeName)
//                         .address(AddressDto.toDto(address))
//                         .time(Times.of(businessHour))
//                         .thumbnailUrl(photoUrl != null ? photoUrl : "PetToYou-Logo")
//                         .build();
////                         .tags()
//             }
}