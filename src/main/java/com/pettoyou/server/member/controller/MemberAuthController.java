package com.pettoyou.server.member.controller;

import com.pettoyou.server.auth.kakao.KakaoLoginParam;
import com.pettoyou.server.auth.naver.NaverLoginParam;
import com.pettoyou.server.constant.dto.ApiResponse;
import com.pettoyou.server.constant.enums.CustomResponseStatus;
import com.pettoyou.server.member.dto.MemberDto;
import com.pettoyou.server.member.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class MemberAuthController {
    private final AuthService authService;

    // 로그인 및 강제 회원가입
    @PostMapping("/kakao")
    public ResponseEntity<ApiResponse<MemberDto.Response.SignIn>> loginKakao(@RequestBody KakaoLoginParam kakaoLoginParam) {
        MemberDto.Response.SignIn signInDto = authService.signIn(kakaoLoginParam);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(signInDto, CustomResponseStatus.SUCCESS));
    }

    @PostMapping("/naver")
    public ResponseEntity<ApiResponse<MemberDto.Response.SignIn>> loginNaver(@RequestBody NaverLoginParam naverLoginParam) {
        MemberDto.Response.SignIn signInDto = authService.signIn(naverLoginParam);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(signInDto, CustomResponseStatus.SUCCESS));
    }

    // 토큰 재발급
    @PostMapping("/reissue")
    public ResponseEntity<ApiResponse<MemberDto.Response.Reissue>> reissue(@RequestHeader("Authorization") String refreshToken) {
        MemberDto.Response.Reissue reissueDto = authService.reissue(refreshToken);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(reissueDto, CustomResponseStatus.SUCCESS));
    }

    // 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<String>> logout(@RequestHeader("Authorization") String accessToken) {
        authService.logout(accessToken);
        return ResponseEntity.ok().body(ApiResponse.createSuccess("Logout Success", CustomResponseStatus.SUCCESS));

    }
}
