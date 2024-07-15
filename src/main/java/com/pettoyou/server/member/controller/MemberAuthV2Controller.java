package com.pettoyou.server.member.controller;

import com.pettoyou.server.constant.dto.ApiResponse;
import com.pettoyou.server.constant.enums.CustomResponseStatus;
import com.pettoyou.server.member.dto.request.LoginReqDto;
import com.pettoyou.server.member.dto.response.LoginRespDto;
import com.pettoyou.server.member.entity.enums.OAuthProvider;
import com.pettoyou.server.member.service.v2.AuthV2Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class MemberAuthV2Controller {
    private final AuthV2Service authV2Service;

    @PostMapping("/login/{provider}")
    public ResponseEntity<ApiResponse<LoginRespDto>> socialLoginWithoutApple(
            @PathVariable OAuthProvider provider,
            @RequestBody LoginReqDto loginReqDto
    ) {
        LoginRespDto response = authV2Service.socialLoginWithoutApple(loginReqDto, provider);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(response, CustomResponseStatus.SUCCESS));
    }

}
