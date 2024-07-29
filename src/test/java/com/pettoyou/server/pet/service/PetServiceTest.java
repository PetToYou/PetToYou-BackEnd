package com.pettoyou.server.pet.service;

import com.pettoyou.server.constant.enums.CustomResponseStatus;
import com.pettoyou.server.constant.exception.CustomException;
import com.pettoyou.server.member.entity.Member;
import com.pettoyou.server.member.entity.enums.MemberStatus;
import com.pettoyou.server.member.entity.enums.OAuthProvider;
import com.pettoyou.server.member.repository.MemberRepository;
import com.pettoyou.server.pet.dto.request.PetMedicalInfoDto;
import com.pettoyou.server.pet.dto.request.PetRegisterAndModifyReqDto;
import com.pettoyou.server.pet.dto.response.PetDetailInfoRespDto;
import com.pettoyou.server.pet.dto.response.PetRegisterRespDto;
import com.pettoyou.server.pet.entity.Pet;
import com.pettoyou.server.pet.entity.enums.*;
import com.pettoyou.server.pet.repository.PetRepository;
import com.pettoyou.server.photo.entity.PhotoData;
import com.pettoyou.server.util.S3Util;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class PetServiceTest {
    @Autowired
    private PetRepository petRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PetService petService;

    @Autowired
    private EntityManager em;

    @MockBean
    private S3Util s3Util;

    /***
     * 반려동물 등록 테스트
     */

    @Test
    void 프로필이미지와_반려동물의_정보를_입력한_경우_정상적으로_등록된다() {
        // given
        PetRegisterAndModifyReqDto petRegisterAndModifyReqDto = createPetFullyDto();
        Member savedMember = createMember();
        Pet pet = createPet(petRegisterAndModifyReqDto, savedMember);

        MockMultipartFile mockProfilePhoto = new MockMultipartFile(
                "file",
                "test.jpg",
                "image/jpeg",
                "Test Image Content".getBytes()
        );

        // when
        PetRegisterRespDto response = petService.petRegister(mockProfilePhoto, petRegisterAndModifyReqDto, savedMember.getMemberId());

        // then
        assertThat(response.petName()).isEqualTo(pet.getPetName());
    }

    @Test
    void 프로필이미지_없이_반려동물의_정보를_입력한_경우_정상적으로_등록된다() {
        // given
        PetRegisterAndModifyReqDto petRegisterAndModifyReqDto = createPetFullyDto();
        Member savedMember = createMember();
        Pet savedPet = createPet(petRegisterAndModifyReqDto, savedMember);

        // when
        PetRegisterRespDto response = petService.petRegister(null, petRegisterAndModifyReqDto, savedMember.getMemberId());

        // then
        assertThat(response.petName()).isEqualTo(savedPet.getPetName());
    }

    @Test
    void 잘못된_유저_ID_로_반려동물_등록_요청을하면_실패한다() {
        // given
        PetRegisterAndModifyReqDto petRegisterAndModifyReqDto = createPetFullyDto();
        Member savedMember = createMember();

        // then
        assertThatExceptionOfType(CustomException.class)
                .isThrownBy(() -> {
                    petService.petRegister(null, petRegisterAndModifyReqDto, savedMember.getMemberId() + 1L);
                })
                .withMessage(CustomResponseStatus.MEMBER_NOT_FOUND.getMessage());
    }

    @Test
    void 입양일을_입력하지_않은_경우_생년월일이_입양일이_된다() {
        // given
        PetRegisterAndModifyReqDto petNoAdoptionDateDto = createPetNoAdoptionDateDto();
        Member savedMember = createMember();
        Pet pet = createPet(petNoAdoptionDateDto, savedMember);

        // when
        LocalDate adoptionDate = pet.getAdoptionDate();

        // then
        assertThat(adoptionDate).isEqualTo(LocalDate.of(2023, 7, 24));
    }

    @Test
    void 이미지가_없다면_uploadFile_메서드가_실행되지_않는다() {
        // given
        PetRegisterAndModifyReqDto petRegisterAndModifyReqDto = createPetFullyDto();
        PetRegisterAndModifyReqDto modifyReqDto = createModifyReqDto();
        Member member = createMember();
        Pet pet = createPet(petRegisterAndModifyReqDto, member);

        // when
        petService.petModify(pet.getPetId(), null, modifyReqDto, member.getMemberId());

        // then
        verify(s3Util, times(0)).uploadFile(any());
    }

    /***
     * 반려동물 수정 테스트
     */

    @Test
    void 정상적인_반려동물_수정요청() {
        // given
        PetRegisterAndModifyReqDto petRegisterAndModifyReqDto = createPetFullyDto();
        PetRegisterAndModifyReqDto modifyReqDto = createModifyReqDto();
        Member member = createMember();
        Pet pet = createPet(petRegisterAndModifyReqDto, member);

        // when
        petService.petModify(pet.getPetId(), null, modifyReqDto, member.getMemberId());
        em.persist(pet);
        em.flush();

        Pet checkPet = petRepository.findById(pet.getPetId()).get();

        // then
        assertThat(checkPet.getPetName()).isEqualTo(modifyReqDto.petName());
    }

    @Test
    void 반려동물_id가_올바르지_않을때_예외발생() {
        // given
        PetRegisterAndModifyReqDto petRegisterAndModifyReqDto = createPetFullyDto();
        Member savedMember = createMember();
        Pet savedPet = createPet(petRegisterAndModifyReqDto, savedMember);
        PetRegisterAndModifyReqDto modifyReqDto = createModifyReqDto();

        // then
        assertThatExceptionOfType(CustomException.class)
                // when
                .isThrownBy(() -> petService.petModify(savedPet.getPetId() + 1, null, modifyReqDto, savedMember.getMemberId()))
                .withMessage(CustomResponseStatus.PET_NOT_FOUND.getMessage());
    }

    @Test
    void 수정_요청_유저_id와_반려동물_주인_id가_다를경우_예외발생() {
        // given
        PetRegisterAndModifyReqDto petRegisterAndModifyReqDto = createPetFullyDto();
        Member savedMember = createMember();
        Pet savedPet = createPet(petRegisterAndModifyReqDto, savedMember);
        PetRegisterAndModifyReqDto modifyReqDto = createModifyReqDto();

        // then
        assertThatExceptionOfType(CustomException.class)
                // when
                .isThrownBy(() -> petService.petModify(savedPet.getPetId(), null, modifyReqDto, savedMember.getMemberId() + 1))
                .withMessage(CustomResponseStatus.MEMBER_NOT_MATCH.getMessage());
    }

    /***
     * 반려동물 삭제 테스트
     */

    @Test
    void 반려동물_정상_삭제() {
        // given
        PetRegisterAndModifyReqDto petRegisterAndModifyReqDto = createPetFullyDto();
        Member savedMember = createMember();
        Pet savedPet = createPet(petRegisterAndModifyReqDto, savedMember);

        // when
        petService.petDelete(savedPet.getPetId(), savedMember.getMemberId());
        em.flush();
        Optional<Pet> fetchPet = petRepository.findById(savedPet.getPetId());

        // then
        assertThat(fetchPet).isEqualTo(Optional.empty());
    }

    @Test
    void 반려동물_id가_올바르지_않을때_예외가_발생한다() {
        // given
        PetRegisterAndModifyReqDto petRegisterAndModifyReqDto = createPetFullyDto();
        Member savedMember = createMember();
        Pet savedPet = createPet(petRegisterAndModifyReqDto, savedMember);

        // then
        assertThatExceptionOfType(CustomException.class)
                .isThrownBy(() -> petService.petDelete(savedPet.getPetId() + 1, savedMember.getMemberId()))
                .withMessage(CustomResponseStatus.PET_NOT_FOUND.getMessage());
    }

    @Test
    void 삭제_요청_유저_id와_반려동물_주인_id가_다를경우_예외발생() {
        // given
        PetRegisterAndModifyReqDto petRegisterAndModifyReqDto = createPetFullyDto();
        Member savedMember = createMember();
        Pet savedPet = createPet(petRegisterAndModifyReqDto, savedMember);

        // then
        assertThatExceptionOfType(CustomException.class)
                .isThrownBy(() -> petService.petDelete(savedPet.getPetId(), savedMember.getMemberId() + 1))
                .withMessage(CustomResponseStatus.MEMBER_NOT_MATCH.getMessage());
    }

    /***
     * 반려동물 조회 테스트
     */

    @Test
    void 반려동물_정상_조회() {
        // given
        List<PetRegisterAndModifyReqDto> petFullyDtoList = createPetFullyDtoList(5);
        Member savedMember = createMember();
        petFullyDtoList.forEach(p -> createPet(p, savedMember));

        // when
        em.flush();
        em.clear();
        int petSize = petRepository.findAllPetsByMemberId(savedMember.getMemberId()).size();

        // then
        assertThat(petSize).isEqualTo(5);
    }

    @Test
    void 반려동물_정상_상세_조회() {
        // given
        Pet savedPet = createPet(createPetFullyDto(), createMember());

        // when
        em.flush();
        em.clear();
        PetDetailInfoRespDto petDetailInfoRespDto = petService.fetchPetDetailInfo(savedPet.getPetId(), savedPet.getMember().getMemberId());

        // then
        assertThat(savedPet.getPetId()).isEqualTo(petDetailInfoRespDto.petId());
    }

    @Test
    void 반려동물_상세조회시_반려동물의_id_값이_잘못된_경우_예외발생() {
        // given
        Pet savedPet = createPet(createPetFullyDto(), createMember());

        em.flush();
        em.clear();

        //then
        assertThatExceptionOfType(CustomException.class)
                // when
                .isThrownBy(() -> petService.fetchPetDetailInfo(savedPet.getPetId() + 1, savedPet.getMember().getMemberId()))
                .withMessage(CustomResponseStatus.PET_NOT_FOUND.getMessage());
    }

    @Test
    void 반려동물_상세조회시_요청_유저의_id와_반려동물의_주인_id값이_다른_경우_예외발생() {
        // given
        Pet savedPet = createPet(createPetFullyDto(), createMember());
        em.flush();
        em.clear();

        //then
        assertThatExceptionOfType(CustomException.class)
                // when
                .isThrownBy(() -> petService.fetchPetDetailInfo(savedPet.getPetId(), savedPet.getMember().getMemberId() + 1))
                .withMessage(CustomResponseStatus.MEMBER_NOT_MATCH.getMessage());
    }

    private PetRegisterAndModifyReqDto createPetFullyDto() {
        return PetRegisterAndModifyReqDto.builder()
                .petType(PetType.DOG)
                .petName("smile")
                .birth(LocalDate.of(2023, 7, 24))
                .adoptionDate(LocalDate.of(2023, 6, 24))
                .gender(Gender.MALE)
                .species(Species.MUNCHKIN)
                .petMedicalInfoDto(
                        PetMedicalInfoDto.builder()
                                .weight(4.5)
                                .bmi(Bmi.NORMAL)
                                .registerNumber("1234")
                                .neuteringStatus(NeuteringStatus.NEUTERED)
                                .vaccinationStatus(VaccinationStatus.NOT_VACCINATED)
                                .allergy("알러지 없어요")
                                .currentFeedName("맛있는 사료")
                                .medicalHistory("수술한적 없음")
                                .build())
                .build();
    }

    private List<PetRegisterAndModifyReqDto> createPetFullyDtoList(int petSize) {
        List<PetRegisterAndModifyReqDto> list = new ArrayList<>(petSize);
        for (int i = 0; i < petSize; i++) {
            list.add(createPetFullyDto());
        }

        return list;
    }

    private PetRegisterAndModifyReqDto createPetNoAdoptionDateDto() {
        return PetRegisterAndModifyReqDto.builder()
                .petType(PetType.DOG)
                .petName("smile")
                .birth(LocalDate.of(2023, 7, 24))
                .gender(Gender.MALE)
                .species(Species.MUNCHKIN)
                .petMedicalInfoDto(
                        PetMedicalInfoDto.builder()
                                .weight(4.5)
                                .bmi(Bmi.NORMAL)
                                .registerNumber("1234")
                                .neuteringStatus(NeuteringStatus.NEUTERED)
                                .vaccinationStatus(VaccinationStatus.NOT_VACCINATED)
                                .allergy("알러지 없어요")
                                .currentFeedName("맛있는 사료")
                                .medicalHistory("수술한적 없음")
                                .build())
                .build();
    }

    private PetRegisterAndModifyReqDto createModifyReqDto() {
        return PetRegisterAndModifyReqDto.builder()
                .petType(PetType.DOG)
                .petName("modifyName")
                .birth(LocalDate.of(2023, 7, 24))
                .gender(Gender.MALE)
                .species(Species.MUNCHKIN)
                .petMedicalInfoDto(
                        PetMedicalInfoDto.builder()
                                .weight(4.5)
                                .bmi(Bmi.NORMAL)
                                .registerNumber("1234")
                                .neuteringStatus(NeuteringStatus.NEUTERED)
                                .vaccinationStatus(VaccinationStatus.NOT_VACCINATED)
                                .allergy("알러지 없어요")
                                .currentFeedName("맛있는 사료")
                                .medicalHistory("수술한적 없음")
                                .build())
                .build();
    }

    private Member createMember() {
        return memberRepository.save(Member.builder()
                .memberId(4L)
                .name("성춘")
                .nickName("choon")
                .phone("010-7592-0693")
                .email("test@naver.com")
                .provider(OAuthProvider.KAKAO)
                .providerId("3535")
                .memberStatus(MemberStatus.ACTIVATE)
                .build());
    }

    private PhotoData createPhotoData() {
        return PhotoData.of(
                "testBucket",
                "testObject",
                "testUrl"
        );
    }

    private Pet createPet(PetRegisterAndModifyReqDto petRegisterAndModifyReqDto, Member member) {
        return petRepository.save(Pet.of(petRegisterAndModifyReqDto, createPhotoData(), member));
    }

}