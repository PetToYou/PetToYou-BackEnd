package com.pettoyou.server.scrap.service;

import com.pettoyou.server.constant.enums.CustomResponseStatus;
import com.pettoyou.server.constant.exception.CustomException;
import com.pettoyou.server.member.entity.Member;
import com.pettoyou.server.member.repository.MemberRepository;
import com.pettoyou.server.scrap.dto.response.ScrapRegistRespDto;
import com.pettoyou.server.scrap.entity.Scrap;
import com.pettoyou.server.scrap.repository.ScrapRepository;
import com.pettoyou.server.store.entity.Store;
import com.pettoyou.server.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScrapServiceImpl implements ScrapService{
    private final ScrapRepository scrapRepository;
    private final MemberRepository memberRepository;
    private final StoreRepository storeRepository;
    @Override
    public ScrapRegistRespDto scrapRegist(Long storeId, Long memberId) {
        Member findMember = memberRepository.findById(memberId).orElseThrow(
                () -> new CustomException(CustomResponseStatus.MEMBER_NOT_FOUND)
        );

        Store findStore = storeRepository.findById(storeId).orElseThrow(
                () -> new CustomException(CustomResponseStatus.HOSPITAL_NOT_FOUND)
        );

        Scrap saveStore = scrapRepository.save(Scrap.of(findMember, findStore));

        return new ScrapRegistRespDto(saveStore.getStore().getStoreName());
    }
}
