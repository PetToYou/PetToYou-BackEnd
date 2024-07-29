package com.pettoyou.server.scrap.service;

import com.pettoyou.server.constant.enums.CustomResponseStatus;
import com.pettoyou.server.constant.exception.CustomException;
import com.pettoyou.server.hospital.entity.Hospital;
import com.pettoyou.server.hospital.repository.HospitalRepository;
import com.pettoyou.server.member.entity.Member;
import com.pettoyou.server.member.repository.MemberRepository;
import com.pettoyou.server.scrap.dto.response.ScrapQueryRespDto;
import com.pettoyou.server.scrap.dto.response.ScrapRegistRespDto;
import com.pettoyou.server.scrap.entity.Scrap;
import com.pettoyou.server.scrap.repository.ScrapRepository;
import com.pettoyou.server.store.entity.Store;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScrapServiceImpl implements ScrapService {
    private final ScrapRepository scrapRepository;
    private final MemberRepository memberRepository;
    private final HospitalRepository hospitalRepository;

    @Override
    public ScrapRegistRespDto scrapRegist(Long storeId, Long memberId) {
        Member findMember = memberRepository.findById(memberId).orElseThrow(() ->
                new CustomException(CustomResponseStatus.MEMBER_NOT_FOUND)
        );
        Hospital findHospital = hospitalRepository.findById(storeId).orElseThrow(
                () -> new CustomException(CustomResponseStatus.HOSPITAL_NOT_FOUND)
        );

        Scrap saveScrap = scrapRepository.save(Scrap.of(findMember, findHospital));

        return new ScrapRegistRespDto(saveScrap.getStore().getStoreName());
    }

    @Override
    public void scrapCancel(Long scrapId, Long authMemberId) {
        Scrap findScrap = scrapRepository.findById(scrapId).orElseThrow(() ->
                new CustomException(CustomResponseStatus.SCRAP_NOT_FOUND)
        );

        Member findMember = memberRepository.findById(authMemberId).orElseThrow(() ->
                new CustomException(CustomResponseStatus.MEMBER_NOT_FOUND)
        );
        findMember.validateMemberAuthorization(authMemberId);

        scrapRepository.delete(findScrap);
    }

    @Override
    public List<ScrapQueryRespDto> fetchScrapStore(Long memberId) {
        return scrapRepository.findScrapListByMemberId(memberId);
    }
}
