package com.pettoyou.server.member.dto.response;

import com.pettoyou.server.member.entity.Member;
import lombok.Builder;

@Builder
public record MemberQueryInfoDto(
        Long memberId,
        String name,
        String nickname,
        String phone,
        String email
) {
    public static MemberQueryInfoDto from(Member member) {
        return MemberQueryInfoDto.builder()
                .memberId(member.getMemberId())
                .name(member.getName())
                .nickname(member.getNickName())
                .phone(member.getPhone())
                .email(member.getEmail())
                .build();
    }
}
