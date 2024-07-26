package com.pettoyou.server.pet.service;

import com.pettoyou.server.constant.enums.CustomResponseStatus;
import com.pettoyou.server.constant.exception.CustomException;
import com.pettoyou.server.member.entity.Member;
import com.pettoyou.server.member.repository.MemberRepository;
import com.pettoyou.server.pet.dto.request.PetRegisterAndModifyReqDto;
import com.pettoyou.server.pet.dto.response.PetDetailInfoRespDto;
import com.pettoyou.server.pet.dto.response.PetRegisterRespDto;
import com.pettoyou.server.pet.dto.response.PetSimpleInfoDto;
import com.pettoyou.server.pet.entity.Pet;
import com.pettoyou.server.pet.repository.PetRepository;
import com.pettoyou.server.photo.entity.PhotoData;
import com.pettoyou.server.util.S3Util;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class PetServiceImpl implements PetService {
    private final S3Util s3Util;
    private final PetRepository petRepository;
    private final MemberRepository memberRepository;

    @Override
    public PetRegisterRespDto petRegister(
            MultipartFile petProfileImg,
            PetRegisterAndModifyReqDto petRegisterDto,
            Long loginMemberId
    ) {
        Member member = memberRepository.findByMemberId(loginMemberId).orElseThrow(
                () -> new CustomException(CustomResponseStatus.MEMBER_NOT_FOUND)
        );

        PhotoData photoData;
        if (petProfileImg != null) {
            photoData = s3Util.uploadFile(petProfileImg);
        } else {
            photoData = PhotoData.generateDefaultPetProfilePhotoData();
        }

        Pet registerPet = Pet.of(petRegisterDto, photoData, member);
        petRepository.save(registerPet);

        return PetRegisterRespDto.from(registerPet.getPetName());
    }

    @Override
    public void petModify(
            Long petId,
            MultipartFile petProfileImg,
            PetRegisterAndModifyReqDto petModifyDto,
            Long loginMemberId
    ) {
        Pet pet = petRepository.findById(petId).orElseThrow(
                () -> new CustomException(CustomResponseStatus.PET_NOT_FOUND)
        );

        if (!Objects.equals(pet.getMember().getMemberId(), loginMemberId)) {
            throw new CustomException(CustomResponseStatus.MEMBER_NOT_MATCH);
        }

        PhotoData newPhotoData;
        if (petProfileImg != null) {
            s3Util.deleteFile(pet.getProfilePhotoData().getBucket(), pet.getProfilePhotoData().getObject());
            newPhotoData = s3Util.uploadFile(petProfileImg);
        } else {
            newPhotoData = PhotoData.generateDefaultPetProfilePhotoData();
        }

        pet.modify(petModifyDto, newPhotoData);
    }

    @Override
    public void petDelete(
            Long petId,
            Long loginMemberId
    ) {
        Pet pet = petRepository.findById(petId).orElseThrow(
                () -> new CustomException(CustomResponseStatus.PET_NOT_FOUND)
        );

        if (!Objects.equals(pet.getMember().getMemberId(), loginMemberId)) {
            throw new CustomException(CustomResponseStatus.MEMBER_NOT_MATCH);
        }

        petRepository.delete(pet);
    }

    @Override
    public List<PetSimpleInfoDto> queryPetList(Long userId) {
        return petRepository.findAllPetsByMemberId(userId);
    }

    @Override
    public PetDetailInfoRespDto fetchPetDetailInfo(
            Long petId,
            Long loginMemberId

    ) {
        Pet pet = petRepository.findById(petId).orElseThrow(() -> new CustomException(CustomResponseStatus.PET_NOT_FOUND));

        if (!Objects.equals(pet.getMember().getMemberId(), loginMemberId)) {
            throw new CustomException(CustomResponseStatus.MEMBER_NOT_MATCH);
        }

        return PetDetailInfoRespDto.from(pet);
    }
}
