package com.pettoyou.server.store.service;


import com.pettoyou.server.store.dto.StorePhotoDto;
import com.pettoyou.server.store.repository.StorePhotoRepository;
import com.pettoyou.server.store.repository.StoreRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class StoreServiceImpl implements StoreService{

    private final StoreRepository storeRepository;


    //store photo
    public List<StorePhotoDto> getAllStorePhoto(Long StoreId){
        
        List<StorePhotoDto> storePhotoList = storeRepository.getStorePhotoListByStoreId(StoreId);
        return storePhotoList;
    }



}
