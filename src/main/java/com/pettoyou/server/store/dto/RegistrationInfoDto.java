package com.pettoyou.server.store.dto;

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
   public static class Request{

       @NotNull
       String ceoName;
       @NotNull
       String ceoPhone;
       @NotNull
       @Email
       String ceoEmail;
       @NotNull
       String businessNumber;
   }
}