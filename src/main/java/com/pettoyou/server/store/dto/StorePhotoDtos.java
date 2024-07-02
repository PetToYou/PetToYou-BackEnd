package com.pettoyou.server.store.dto;

import com.pettoyou.server.constant.enums.BaseStatus;
import com.pettoyou.server.store.entity.StorePhoto;
import com.pettoyou.server.store.entity.enums.StoreType;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDateTime;

/**
 * DTO for {@link com.pettoyou.server.store.entity.StorePhoto}
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StorePhotoDtos {

    LocalDateTime createdAt;
    LocalDateTime modifiedAt;
    Long storePhotoId;
    StoreType storeType;
    String storePhotoUrl;

    @Positive
    Integer photoOrder;

    BaseStatus photoStatus;

    Long storeId;


    public static StorePhotoDtos toDto(StorePhoto storePhoto){

        return StorePhotoDtos.builder()
                .createdAt(storePhoto.getCreatedAt())
                .modifiedAt(storePhoto.getModifiedAt())
                .storePhotoId(storePhoto.getStorePhotoId())
                .storeType(storePhoto.getStoreType())
                .storePhotoUrl(storePhoto.getStorePhotoUrl())
                .photoOrder(storePhoto.getPhotoOrder())
                .photoStatus(storePhoto.getPhotoStatus())
                .storeId(storePhoto.getStore().getStoreId())
                .build();

    }
}