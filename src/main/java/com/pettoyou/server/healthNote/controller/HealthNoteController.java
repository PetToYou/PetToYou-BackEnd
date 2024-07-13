package com.pettoyou.server.healthNote.controller;

import com.pettoyou.server.config.security.service.PrincipalDetails;
import com.pettoyou.server.constant.dto.ApiResponse;
import com.pettoyou.server.constant.enums.CustomResponseStatus;
import com.pettoyou.server.healthNote.dto.request.HealthNoteRegistReqDto;
import com.pettoyou.server.healthNote.service.HealthNoteCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member/")
public class HealthNoteController {
    private final HealthNoteCommandService healthNoteCommandService;

    @PostMapping("healthNote")
    public ResponseEntity<ApiResponse<String>> registHealthNote(
            @RequestBody HealthNoteRegistReqDto registReqDto,
            @AuthenticationPrincipal PrincipalDetails principalDetails
    ) {
        healthNoteCommandService.registHealthNote(registReqDto, principalDetails.getUserId());
        return ResponseEntity.ok().body(ApiResponse.createSuccess("등록완료", CustomResponseStatus.SUCCESS));
    }
}
