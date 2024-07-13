package com.pettoyou.server.healthNote.controller;

import com.pettoyou.server.config.security.service.PrincipalDetails;
import com.pettoyou.server.constant.dto.ApiResponse;
import com.pettoyou.server.constant.enums.CustomResponseStatus;
import com.pettoyou.server.healthNote.dto.request.HealthNoteRegistAndModifyReqDto;
import com.pettoyou.server.healthNote.service.HealthNoteCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member/")
public class HealthNoteController {
    private final HealthNoteCommandService healthNoteCommandService;

    @PostMapping("healthNote")
    public ResponseEntity<ApiResponse<String>> registHealthNote(
            @RequestBody HealthNoteRegistAndModifyReqDto registReqDto,
            @AuthenticationPrincipal PrincipalDetails principalDetails
    ) {
        healthNoteCommandService.registHealthNote(registReqDto, principalDetails.getUserId());
        return ResponseEntity.ok().body(ApiResponse.createSuccess("등록완료", CustomResponseStatus.SUCCESS));
    }

    @PutMapping("healthNote/{healthNoteId}")
    public ResponseEntity<ApiResponse<String>> modifyHealthNote(
            @PathVariable Long healthNoteId,
            @RequestBody HealthNoteRegistAndModifyReqDto modifyReqDto,
            @AuthenticationPrincipal PrincipalDetails principalDetails
    ) {
        healthNoteCommandService.modifyHealthNote(healthNoteId, modifyReqDto, principalDetails.getUserId());
        return ResponseEntity.ok().body(ApiResponse.createSuccess("수정완료", CustomResponseStatus.SUCCESS));
    }

    @DeleteMapping("healthNote/{healthNoteId}")
    public ResponseEntity<ApiResponse<String>> deleteHealthNote(
            @PathVariable Long healthNoteId,
            @AuthenticationPrincipal PrincipalDetails principalDetails
    ) {
        healthNoteCommandService.deleteHealthNote(healthNoteId, principalDetails.getUserId());
        return ResponseEntity.ok().body(ApiResponse.createSuccess("삭제완료", CustomResponseStatus.SUCCESS));
    }
}
