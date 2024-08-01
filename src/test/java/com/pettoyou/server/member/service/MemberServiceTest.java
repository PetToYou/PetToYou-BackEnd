package com.pettoyou.server.member.service;

import com.pettoyou.server.member.dto.request.MemberInfoModifyReqDto;
import com.pettoyou.server.member.entity.Member;
import com.pettoyou.server.member.entity.enums.MemberStatus;
import com.pettoyou.server.member.entity.enums.OAuthProvider;
import com.pettoyou.server.member.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberServiceImpl memberService;

    @Test
    void 유저_정보_정상수정() {
        // given
        Member member = createMember();
        MemberInfoModifyReqDto modifyReqDto = createModifyReqDto();

        Mockito.when(memberRepository.findById(member.getMemberId())).thenReturn(Optional.of(member));

        // when
        memberService.modifyMemberInfo(modifyReqDto, member.getMemberId());

        // then
        Assertions.assertThat(member.getNickName()).isEqualTo(modifyReqDto.newNickname());
    }

    private MemberInfoModifyReqDto createModifyReqDto() {
        return new MemberInfoModifyReqDto("newNickname");
    }

    private Member createMember() {
        return Member.builder()
                .memberId(1L)
                .name("성춘")
                .nickName("choon")
                .phone("010-7592-0693")
                .email("test@naver.com")
                .provider(OAuthProvider.KAKAO)
                .providerId("3535")
                .memberStatus(MemberStatus.ACTIVATE)
                .build();
    }

}