package com.pettoyou.server.member.service;

import com.pettoyou.server.constant.enums.CustomResponseStatus;
import com.pettoyou.server.constant.exception.CustomException;
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

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

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

        when(memberRepository.findById(any(Long.class))).thenReturn(Optional.of(member));

        // when
        memberService.modifyMemberInfo(modifyReqDto, member.getMemberId());

        // then
        assertThat(member.getNickName()).isEqualTo(modifyReqDto.newNickname());
    }

    @Test
    void 유저_id가_잘못된_경우_수정요청시_예외_발생() {
        // given
        Member member = createMember();
        MemberInfoModifyReqDto modifyReqDto = createModifyReqDto();
        long wrongMemberId = member.getMemberId() + 1;

        when(memberRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        // then
        assertThatExceptionOfType(CustomException.class)
                // when
                .isThrownBy(() -> memberService.modifyMemberInfo(modifyReqDto, wrongMemberId))
                .withMessage(CustomResponseStatus.MEMBER_NOT_FOUND.getMessage());
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