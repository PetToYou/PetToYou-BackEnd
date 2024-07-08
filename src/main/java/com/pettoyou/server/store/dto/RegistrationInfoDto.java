package com.pettoyou.server.store.dto;

import com.pettoyou.server.store.entity.RegistrationInfo;
import com.pettoyou.server.store.entity.Store;
import com.pettoyou.server.store.entity.enums.StoreType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;

/**
 * DTO for {@link com.pettoyou.server.store.entity.RegistrationInfo}
 */


public class RegistrationInfoDto{


    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Request {
        @NotNull
        String ceoName;
        @NotNull
        String ceoPhone;
        @NotNull
        @Email
        String ceoEmail;
        @NotNull
        String businessNumber;

        public static RegistrationInfo toEntity (RegistrationInfoDto.Request reg, StoreType stoereType){
            RegistrationInfo registrationInfo = RegistrationInfo.builder()
                    .ceoName(reg.ceoName)
                    .ceoPhone(reg.ceoPhone)
                    .ceoEmail(reg.ceoEmail)
                    .businessNumber(reg.businessNumber)
                    .storeType(stoereType)
                    .build();

            return registrationInfo;
        }
    }
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        @NotNull
        String ceoName;
        @NotNull
        String ceoPhone;
        @NotNull
        @Email
        String ceoEmail;
        @NotNull
        String businessNumber;
        @Enumerated
        StoreType storeType;

        public static RegistrationInfoDto.Response toRegistrationInfoDto (RegistrationInfo reg){
            RegistrationInfoDto.Response registrationInfoDto = RegistrationInfoDto.Response.builder()
                    .ceoName(reg.getCeoName())
                    .ceoPhone(reg.getCeoPhone())
                    .ceoEmail(reg.getCeoEmail())
                    .businessNumber(reg.getBusinessNumber())
                    .storeType(reg.getStoreType())
                    .build();


            return registrationInfoDto;
        }
    }

}

//

