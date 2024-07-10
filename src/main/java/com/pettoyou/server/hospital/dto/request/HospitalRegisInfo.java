package com.pettoyou.server.hospital.dto.request;

import com.pettoyou.server.store.dto.BusinessHourDtos;
import com.pettoyou.server.store.dto.RegistrationInfoDto;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record HospitalRegisInfo(
        @NotNull String hospitalName,
        @NotNull String hospitalPhone,
        String notice,
        String additionalServiceTag,
        String websiteLink,
        String hospitalInfo,
//        private String hospitalInfoPhoto; -> s3 이미지 ??? what?
        @NotNull String zipCode,
        @NotNull String sido,
        @NotNull String sigungu,
        String eupmyun,
        @NotNull String doro,
        @NotNull double longitude,
        @NotNull double latitude,
        List<BusinessHourDtos> businessHours,
        RegistrationInfoDto.Request registrationInfo
) {
}
