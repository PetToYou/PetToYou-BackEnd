package com.pettoyou.server.pet.dto.response;

import com.pettoyou.server.pet.dto.request.PetRegisterReqDto;

public record PetRegisterRespDto(
        String petName
) {
    public static PetRegisterRespDto from(String petName) {
        return new PetRegisterRespDto(petName);
    }
}
