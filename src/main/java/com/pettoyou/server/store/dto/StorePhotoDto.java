package com.pettoyou.server.store.dto;

import com.pettoyou.server.constant.enums.BaseStatus;
import com.pettoyou.server.photo.entity.PhotoData;
import com.pettoyou.server.store.entity.StorePhoto;
import com.pettoyou.server.store.entity.enums.StoreType;
import jakarta.persistence.Embedded;
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
public class StorePhotoDto {

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    private Long storePhotoId;

    private StoreType storeType;

    private PhotoData storePhoto;

    @Positive
    private Integer photoOrder;

    private BaseStatus photoStatus;

    private Long storeId;

    public static StorePhotoDto toDto(StorePhoto storePhoto) {

        return StorePhotoDto.builder()
                .createdAt(storePhoto.getCreatedAt())
                .modifiedAt(storePhoto.getModifiedAt())
                .storePhotoId(storePhoto.getStorePhotoId())
                .storeType(storePhoto.getStoreType())
                .storePhoto(storePhoto.getStorePhoto())
                .photoOrder(storePhoto.getPhotoOrder())
                .photoStatus(storePhoto.getPhotoStatus())
                .storeId(storePhoto.getStore().getStoreId())
                .build();

    }
}