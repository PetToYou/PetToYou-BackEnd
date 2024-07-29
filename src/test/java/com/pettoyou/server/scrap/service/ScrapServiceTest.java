package com.pettoyou.server.scrap.service;

import com.pettoyou.server.hospital.entity.Hospital;
import com.pettoyou.server.hospital.repository.HospitalRepository;
import com.pettoyou.server.member.entity.Member;
import com.pettoyou.server.member.entity.enums.MemberStatus;
import com.pettoyou.server.member.entity.enums.OAuthProvider;
import com.pettoyou.server.member.repository.MemberRepository;
import com.pettoyou.server.scrap.dto.response.ScrapRegistRespDto;
import com.pettoyou.server.scrap.entity.Scrap;
import com.pettoyou.server.scrap.repository.ScrapRepository;
import com.pettoyou.server.store.entity.Store;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ScrapServiceTest {

    @Mock
    private ScrapRepository scrapRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private HospitalRepository hospitalRepository;

    @InjectMocks
    private ScrapServiceImpl scrapService;

    @Test
    void 스크랩_정상_등록() {
        // given
        Member member = createMember();
        Hospital hospital = createHospital();
        Scrap scrap = createScrap(member, hospital);

        when(memberRepository.findById(any(Long.class))).thenReturn(Optional.of(member));
        when(hospitalRepository.findById(any(Long.class))).thenReturn(Optional.of(hospital));
        when(scrapRepository.save(any(Scrap.class))).thenReturn(scrap);

        // when
        ScrapRegistRespDto result = scrapService.scrapRegist(hospital.getStoreId(), member.getMemberId());

        // then
        assertThat(result.storeName()).isEqualTo(hospital.getStoreName());
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

    private Hospital createHospital() {
        return Hospital.builder()
                .storeId(1L)
                .storeName("testHospital")
                .build();
    }

    private Scrap createScrap(Member member, Store store) {
        return Scrap.of(member, store);
    }
}