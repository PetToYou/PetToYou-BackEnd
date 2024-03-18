package com.pettoyou.server.member.controller;

import com.pettoyou.server.constant.dto.ApiResponse;
import com.pettoyou.server.member.dto.MemberDto;
import com.pettoyou.server.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class MemberAuthController {
    private final MemberService memberService;

    // 로그인 및 강제 회원가입
//    @PostMapping("/kakao")
//    public ResponseEntity<ApiResponse<MemberDto.Response.SignIn>> loginKakao() {
//
//    }



    // 토큰 재발급

    // 로그아웃

}
