package com.pettoyou.server.member.service;

import com.pettoyou.server.member.dto.request.MemberInfoModifyReqDto;
import com.pettoyou.server.member.dto.response.MemberInfoQueryDto;

public interface MemberService {

    void modifyMemberInfo(
            MemberInfoModifyReqDto modifyDto,
            Long authMemberId
    );

    MemberInfoQueryDto queryMemberInfo(Long authMemberId);
}
