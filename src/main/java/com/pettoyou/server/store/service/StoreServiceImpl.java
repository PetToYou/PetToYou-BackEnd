package com.pettoyou.server.store.service;

import com.pettoyou.server.constant.enums.CustomResponseStatus;
import com.pettoyou.server.constant.exception.CustomException;
import com.pettoyou.server.store.dto.response.StorePhotoDto;
import com.pettoyou.server.store.entity.StorePhoto;
import com.pettoyou.server.store.repository.StorePhotoRepository;
import com.pettoyou.server.store.repository.StoreRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class StoreServiceImpl implements StoreService{

    private final StorePhotoRepository storePhotoRepository;


    //store photo
    public List<StorePhotoDto> getAllStorePhoto(Long storeId){
        List<StorePhoto> photos =  storePhotoRepository.findStorePhotosByStoreStoreId(storeId);
        if(photos.isEmpty()){
            throw new CustomException(CustomResponseStatus.STOREPHOTO_NOT_FOUND);
        }
        return photos.stream().map(StorePhotoDto::toDto).toList();
    }
}
