package com.pettoyou.server.hospital.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pettoyou.server.store.dto.response.AddressDto;
import com.pettoyou.server.store.dto.response.TagInfo;
import com.pettoyou.server.store.entity.Address;
import com.pettoyou.server.store.entity.BusinessHour;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

/**
 * DTO for {@link com.pettoyou.server.hospital.entity.Hospital}
 */
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public record HospitalDtoWithAddress(@NotNull Long storeId,
                                     @NotNull @Size(min = 2) String storeName,
                                     String thumbnailUrl,
                                     AddressDto addressDto,
                                     //response/dto
                                     Times time,
                                     TagInfo tags)
         {
             public HospitalDtoWithAddress(Long storeId,
                                           String storeName,
                                           String thumbnailUrl,
                                           Address address,
                                           BusinessHour businessHour) {
                 this(storeId, storeName, thumbnailUrl, AddressDto.toDto(address), businessHour!=null? Times.of(businessHour): null, null);
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