package com.pettoyou.server.reserve.controller;

import com.pettoyou.server.constant.dto.ApiResponse;
import com.pettoyou.server.constant.enums.CustomResponseStatus;
import com.pettoyou.server.reserve.dto.HealthNoteDto;
import com.pettoyou.server.reserve.service.healthNote.HealthNoteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/member")
public class HealthNoteController {
    private final HealthNoteService healthNoteService;

    // TODO : 건강수첩 등록
    @PostMapping("/medical")
    public ResponseEntity<ApiResponse<String>> healthNoteRegister(
        @RequestBody HealthNoteDto.Request.Register registerDto
    ) {
        log.info(registerDto.toString());
        healthNoteService.register(registerDto);
        return ResponseEntity.ok().body(ApiResponse.createSuccess("건강수첩 등록이 완료되었습니다.", CustomResponseStatus.SUCCESS));

    }

    // TODO : 건강수첩 수정

    // TODO : 건강수첩 삭제

    // TODO : 건강수첩 조회
}
