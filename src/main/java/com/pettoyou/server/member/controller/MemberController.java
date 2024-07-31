package com.pettoyou.server.member.controller;

import com.pettoyou.server.config.security.service.PrincipalDetails;
import com.pettoyou.server.constant.dto.ApiResponse;
import com.pettoyou.server.member.dto.response.MemberQueryInfoDto;
import com.pettoyou.server.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    /***
     * 내 정보 수정하기
     */

    /***
     * 내 정보 조회하기
     */
    @GetMapping("/myPage")
    public ResponseEntity<ApiResponse<MemberQueryInfoDto>> queryMemberInfo(
            @AuthenticationPrincipal PrincipalDetails principalDetails
    ) {
        MemberQueryInfoDto response = memberService.queryMemberInfo(principalDetails.getUserId());

        return ApiResponse.createSuccessWithOk(response);
    }
}
