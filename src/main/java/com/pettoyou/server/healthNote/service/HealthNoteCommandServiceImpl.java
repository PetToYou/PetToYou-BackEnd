package com.pettoyou.server.healthNote.service;

import com.pettoyou.server.constant.enums.CustomResponseStatus;
import com.pettoyou.server.constant.exception.CustomException;
import com.pettoyou.server.healthNote.dto.request.HealthNoteRegistReqDto;
import com.pettoyou.server.healthNote.entity.HealthNote;
import com.pettoyou.server.healthNote.repository.HealthNoteRepository;
import com.pettoyou.server.member.repository.MemberRepository;
import com.pettoyou.server.pet.repository.PetRepository;
import com.pettoyou.server.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class HealthNoteCommandServiceImpl implements HealthNoteCommandService {
    private final MemberRepository memberRepository;
    private final HealthNoteRepository healthNoteRepository;
    private final StoreRepository storeRepository;
    private final PetRepository petRepository;

    @Override
    public void registHealthNote(HealthNoteRegistReqDto healthNoteRegistReqDto, Long memberId) {
        // Todo : 준혁이거 pull받아오면 store type 비교해서 진짜 병원인지 따져보기
        storeRepository.findById(healthNoteRegistReqDto.hospitalId()).orElseThrow(() ->
                new CustomException(CustomResponseStatus.HOSPITAL_NOT_FOUND)
        );

        petRepository.findPetUsingPetIdAndMemberId(healthNoteRegistReqDto.petId(), memberId).orElseThrow(() ->
                new CustomException(CustomResponseStatus.PET_NOT_FOUND)
        );

        healthNoteRepository.save(HealthNote.of(healthNoteRegistReqDto, memberId));
    }
}
