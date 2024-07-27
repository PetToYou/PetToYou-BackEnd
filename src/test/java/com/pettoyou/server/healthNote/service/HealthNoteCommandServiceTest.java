package com.pettoyou.server.healthNote.service;

import com.pettoyou.server.healthNote.dto.request.HealthNoteRegistAndModifyReqDto;
import com.pettoyou.server.healthNote.entity.HealthNote;
import com.pettoyou.server.healthNote.repository.HealthNoteRepository;
import com.pettoyou.server.hospital.entity.Hospital;
import com.pettoyou.server.hospital.repository.HospitalRepository;
import com.pettoyou.server.member.entity.Member;
import com.pettoyou.server.member.entity.enums.MemberStatus;
import com.pettoyou.server.member.entity.enums.OAuthProvider;
import com.pettoyou.server.pet.entity.Pet;
import com.pettoyou.server.pet.entity.enums.Gender;
import com.pettoyou.server.pet.entity.enums.PetType;
import com.pettoyou.server.pet.repository.PetRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HealthNoteCommandServiceTest {
    @Mock
    private HospitalRepository hospitalRepository;

    @Mock
    private PetRepository petRepository;

    @Mock
    private HealthNoteRepository healthNoteRepository;

    @InjectMocks
    private HealthNoteCommandServiceImpl healthNoteCommandService;

    @Test
    void 건강수첩_정상_등록() {
        // given
        Hospital hospital = createHospital();
        Pet pet = createPet();
        Member member = createMember();
        HealthNoteRegistAndModifyReqDto registerDto = createHealthNoteRegistAndOrModifyDto();

        when(hospitalRepository.findById(any(Long.class))).thenReturn(Optional.of(hospital));
        when(petRepository.findPetUsingPetIdAndMemberId(any(Long.class), any(Long.class))).thenReturn(Optional.of(pet));

        // when
        healthNoteCommandService.registHealthNote(registerDto, member.getMemberId());

        // then
        verify(healthNoteRepository, times(1)).save(any(HealthNote.class));

        // 추가 검증: 저장된 HealthNote가 예상한 데이터인지 확인
        ArgumentCaptor<HealthNote> captor = ArgumentCaptor.forClass(HealthNote.class);
        verify(healthNoteRepository).save(captor.capture());
        HealthNote savedHealthNote = captor.getValue();

        assertThat(savedHealthNote.getVisitDate()).isEqualTo(registerDto.visitDate());
        assertThat(savedHealthNote.getMedicalRecord()).isEqualTo(registerDto.medicalRecord());
        assertThat(savedHealthNote.getCaution()).isEqualTo(registerDto.caution());
        assertThat(savedHealthNote.getVetName()).isEqualTo(registerDto.vetName());
        assertThat(savedHealthNote.getStoreId()).isEqualTo(registerDto.hospitalId());
        assertThat(savedHealthNote.getPetId()).isEqualTo(registerDto.petId());
        assertThat(savedHealthNote.getMemberId()).isEqualTo(member.getMemberId());
    }



    private HealthNoteRegistAndModifyReqDto createHealthNoteRegistAndOrModifyDto() {
        return HealthNoteRegistAndModifyReqDto.builder()
                .hospitalId(1L)
                .petId(1L)
                .visitDate(LocalDate.of(2024, 7, 27))
                .medicalRecord("testRecord")
                .caution("testCaution")
                .vetName("testVet")
                .build();
    }

    private Member createMember() {
        return Member.builder()
                .memberId(1L)
                .name("testName")
                .phone("010-1111-1111")
                .email("test@naver.com")
                .provider(OAuthProvider.KAKAO)
                .providerId("1234")
                .memberStatus(MemberStatus.ACTIVATE)
                .build();
    }

    private Hospital createHospital() {
        return Hospital.builder()
                .storeId(1L)
                .storeName("testHospital")
                .build();
    }

    private Pet createPet() {
        return Pet.builder()
                .petId(1L)
                .petName("testPet")
                .birth(LocalDate.of(2023, 7, 27))
                .petType(PetType.DOG)
                .gender(Gender.MALE)
                .build();
    }
}