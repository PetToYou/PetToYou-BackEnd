package com.pettoyou.server.hospital.dto;

import com.pettoyou.server.hospital.entity.Hospital;
import com.pettoyou.server.store.entity.BusinessHour;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;


public class HospitalDto {

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

        //초기화?


            //hospitalList에서 들어갈 정보

            private String storeId;
            @NotNull
            private String hospitalName;
            private String openHour;
            private String closeHour;

            //1번 사진
            private String thumbnailUrl;

//            private double rate;
//            private Long reviewCount;
            private Double distance;

            //specialities



    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Test{
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
        private String breakTime;


        private Long reviewCount;

        @Builder.Default
        private Double rateAvg =0.0;
    }

}
