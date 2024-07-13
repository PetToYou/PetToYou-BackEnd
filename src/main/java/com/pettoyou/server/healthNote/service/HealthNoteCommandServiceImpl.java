package com.pettoyou.server.healthNote.service;

import com.pettoyou.server.constant.enums.CustomResponseStatus;
import com.pettoyou.server.constant.exception.CustomException;
import com.pettoyou.server.healthNote.dto.request.HealthNoteRegistAndModifyReqDto;
import com.pettoyou.server.healthNote.entity.HealthNote;
import com.pettoyou.server.healthNote.repository.HealthNoteRepository;
import com.pettoyou.server.pet.repository.PetRepository;
import com.pettoyou.server.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class HealthNoteCommandServiceImpl implements HealthNoteCommandService {
    private final HealthNoteRepository healthNoteRepository;
    private final StoreRepository storeRepository;
    private final PetRepository petRepository;

    @Override
    @Transactional
    public void registHealthNote(HealthNoteRegistAndModifyReqDto registReqDto, Long authMemberId) {
        checkValidHospital(registReqDto.hospitalId());
        checkValidPet(registReqDto.petId(), authMemberId);

        healthNoteRepository.save(HealthNote.of(registReqDto, authMemberId));
    }

    @Override
    @Transactional
    public void modifyHealthNote(Long healthNoteId, HealthNoteRegistAndModifyReqDto modifyReqDto, Long authMemberId) {
        HealthNote findHealthNote = healthNoteRepository.findById(healthNoteId).orElseThrow(() ->
                new CustomException(CustomResponseStatus.HEALTH_NOTE_NOT_FOUND)
        );

        checkMemberAuthorization(findHealthNote.getMemberId(), authMemberId);
        checkValidHospital(modifyReqDto.hospitalId());
        checkValidPet(modifyReqDto.petId(), authMemberId);

        findHealthNote.modifyHealthNote(modifyReqDto);
    }

    private void checkValidHospital(Long hospitalId) {
        // Todo : 준혁이거 pull받아오면 store type 비교해서 진짜 병원인지 따져보기
        storeRepository.findById(hospitalId).orElseThrow(() ->
                new CustomException(CustomResponseStatus.HOSPITAL_NOT_FOUND)
        );
    }

    private void checkValidPet(Long petId, Long authMemberId) {
        petRepository.findPetUsingPetIdAndMemberId(petId, authMemberId).orElseThrow(() ->
                new CustomException(CustomResponseStatus.PET_NOT_FOUND)
        );
    }

    private void checkMemberAuthorization(Long healthNoteWriterId, Long authMemberId) {
        log.info("글쓴이 : {}, 로그인한 애 : {}", healthNoteWriterId, authMemberId);
        if (!healthNoteWriterId.equals(authMemberId)) {
            throw new CustomException(CustomResponseStatus.MEMBER_NOT_MATCH);
        }
    }
}
