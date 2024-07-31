package com.pettoyou.server.member.service;

import com.pettoyou.server.member.dto.response.MemberQueryInfoDto;

public interface MemberService {

    MemberQueryInfoDto queryMemberInfo(Long memberId);
}
