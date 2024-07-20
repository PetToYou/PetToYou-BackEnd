package com.pettoyou.server.member.controller;

import com.pettoyou.server.auth.kakao.KakaoLoginParam;
import com.pettoyou.server.auth.naver.NaverLoginParam;
import com.pettoyou.server.constant.dto.ApiResponse;
import com.pettoyou.server.constant.entity.AuthTokens;
import com.pettoyou.server.constant.enums.CustomResponseStatus;
import com.pettoyou.server.member.dto.MemberDto;
import com.pettoyou.server.member.dto.response.LoginRespDto;
import com.pettoyou.server.member.service.auth.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/auth")
public class MemberAuthController {
    private final AuthService authService;

    // 로그인 및 강제 회원가입
    @PostMapping("/kakao")
    public ResponseEntity<ApiResponse<LoginRespDto>> loginKakao(
            @RequestBody KakaoLoginParam kakaoLoginParam,
            HttpServletResponse response
    ) {
        AuthTokens authTokens = authService.signIn(kakaoLoginParam);

        Cookie refreshTokenCookie = new Cookie("refreshToken", authTokens.refreshToken());
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setPath("/");
//        refreshTokenCookie.setSecure(true); Todo : 추후 https 적용후 주석 풀기
        response.addCookie(refreshTokenCookie);

        return ResponseEntity.ok()
                .body(ApiResponse.createSuccess(LoginRespDto.from(authTokens), CustomResponseStatus.SUCCESS));
    }

    @PostMapping("/naver")
    public ResponseEntity<ApiResponse<LoginRespDto>> loginNaver(@RequestBody NaverLoginParam naverLoginParam) {
        AuthTokens authTokens = authService.signIn(naverLoginParam);

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, authTokens.refreshToken())
                .body(ApiResponse.createSuccess(LoginRespDto.from(authTokens), CustomResponseStatus.SUCCESS));
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
