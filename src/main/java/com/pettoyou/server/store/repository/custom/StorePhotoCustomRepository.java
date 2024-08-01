package com.pettoyou.server.store.repository.custom;

import com.pettoyou.server.store.entity.StorePhoto;

import java.util.List;

public interface StorePhotoCustomRepository {
     List<StorePhoto> getStorePhotosOrderByPhotoOrder(Long storeId);
}
