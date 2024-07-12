package com.pettoyou.server.scrap.dto.response;

import com.pettoyou.server.store.entity.Address;
import lombok.Builder;

@Builder
public record ScrapQueryRespDto(
        String thumbnailUrl,
        String storeName,
        String address
) {
    public ScrapQueryRespDto(String thumbnailUrl, String storeName, Address address) {
        this(thumbnailUrl, storeName, address.generateDefaultAddressFormat());
    }
}
