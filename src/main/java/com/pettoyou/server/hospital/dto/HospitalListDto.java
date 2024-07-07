package com.pettoyou.server.hospital.dto;

import com.pettoyou.server.store.interfaces.StoreInterface;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public class HospitalListDto {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request{

        private double latitude;  // 위도 y
        private double longitude; // 경도 x

        @Builder.Default
        private Integer radius = 5000;

        public String toPointString(){
            return  String.format("POINT (%f %f)", this.latitude, this.longitude);
        }


    }



    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response{
        private String storeId;

        @NotNull
        private String hospitalName;

        @Builder.Default
        private String thumbnailUrl="PetToYou-Logo.png";

        private String distance;

        @Builder.Default
        private String businessHourStatus="몰라";

        @Builder.Default
        private String openHour= LocalDateTime.now().toString();
        @Builder.Default
        private String closeHour = LocalDateTime.now().toString();

        @Nullable
        private String breakStartTime;

        @Nullable
        private String breakEndTime;

        @Builder.Default
        private Long reviewCount = 0L;

        @Builder.Default
        private Double rateAvg =0.0;





        public static List<HospitalListDto.Response> toListDto(final List<StoreInterface> hospitals){

                    List<HospitalListDto.Response> result = hospitals.stream()
                            .filter(h -> h.getHospitalName() != null)
                            .map(h ->
                    Response
                    .builder()
                    .storeId(h.getStoreId())
                    .hospitalName(h.getHospitalName())
                    .thumbnailUrl(h.getThumbnail().getPhotoUrl())
                    .distance(String.format("%.1f", h.getDistance() / 1000.0))
                    .openHour(
                            Optional
                                    .ofNullable(h.getBusinessHours())
                                    .map(bh -> bh.getStartTime())
                                    .map(Object::toString)
                                    .orElse("영업 시간 정보 없음")
                    )
                    .closeHour(
                            Optional
                                    .ofNullable(h.getBusinessHours())
                                    .map(bh -> bh.getEndTime())
                                    .map(Object::toString)
                                    .orElse("영업 시간 정보 없음")
                    )
                            .breakStartTime(
                                    Optional
                                            .ofNullable(h.getBusinessHours())
                                            .map(bh -> bh.getBreakStartTime())
                                            .map(Object::toString)
                                            .orElse("No Break Start Time")
                            )
                    .breakEndTime(
                            Optional
                                    .ofNullable(h.getBusinessHours())
                                    .map(bh -> bh.getBreakEndTime())
                                    .map(Object::toString)
                                    .orElse("No Break End Time")
                    )
                    .reviewCount(h.getReviewCount())
                    .rateAvg(h.getRateAvg())
                    .build())
                    .collect(Collectors.toList());

        return result;

        }
    }

}
