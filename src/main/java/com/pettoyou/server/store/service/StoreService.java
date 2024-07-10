package com.pettoyou.server.store.service;

import com.pettoyou.server.store.dto.StorePhotoDto;

import java.util.List;

public interface StoreService {


    List<StorePhotoDto> getAllStorePhoto(Long StoreId);

}
