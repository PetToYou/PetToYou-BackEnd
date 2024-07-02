package com.pettoyou.server.store.service;

import com.pettoyou.server.store.dto.StorePhotoDtos;

import java.util.List;

public interface StoreService {


    List<StorePhotoDtos> getAllStorePhoto(Long StoreId);

}
