package com.pettoyou.server.store.controller;

import com.pettoyou.server.constant.dto.ApiResponse;
import com.pettoyou.server.hospital.dto.request.HospitalQueryAddressInfo;
import com.pettoyou.server.hospital.dto.request.HospitalQueryCond;
import com.pettoyou.server.hospital.dto.response.HospitalDtoWithDistance;
import com.pettoyou.server.store.dto.response.StorePhotoDto;
import com.pettoyou.server.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/store")
public class StoreController {

    private final StoreService storeService;
    @GetMapping("/{storeId}/photo")
    public ResponseEntity<ApiResponse<List<StorePhotoDto>>> getStorePhotoList(
            @PathVariable Long storeId
    ){
        List<StorePhotoDto> response = storeService.getAllStorePhoto(storeId);

        return ApiResponse.createSuccessWithOk(response);
    }
}
