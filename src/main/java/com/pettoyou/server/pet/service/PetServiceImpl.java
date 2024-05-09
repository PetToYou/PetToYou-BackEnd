package com.pettoyou.server.pet.service;

import com.pettoyou.server.constant.enums.CustomResponseStatus;
import com.pettoyou.server.constant.exception.CustomException;
import com.pettoyou.server.member.entity.Member;
import com.pettoyou.server.member.repository.MemberRepository;
import com.pettoyou.server.pet.dto.PetDto;
import com.pettoyou.server.pet.entity.Pet;
import com.pettoyou.server.pet.entity.PetProfilePhoto;
import com.pettoyou.server.pet.repository.PetPhotoRepository;
import com.pettoyou.server.pet.repository.PetRepository;
import com.pettoyou.server.photo.entity.PhotoData;
import com.pettoyou.server.util.S3Util;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PetServiceImpl implements PetService {
    private final S3Util s3Util;
    private final PetRepository petRepository;
    private final PetPhotoRepository petPhotoRepository;
    private final MemberRepository memberRepository;

    @Override
    public PetDto.Response.Register petRegister(List<MultipartFile> petProfileImgs, PetDto.Request.Register petRegisterDto, Long loginMemberId) {
        Member member = memberRepository.findByMemberId(loginMemberId).orElseThrow(() -> new CustomException(CustomResponseStatus.MEMBER_NOT_FOUND));

        Pet registerPet = Pet.toEntity(petRegisterDto, member);

        petRepository.save(registerPet);

        if (!petProfileImgs.isEmpty()) {
            for (MultipartFile petProfileImg : petProfileImgs) {
                PhotoData photoData = s3Util.uploadFile(petProfileImg);
                PetProfilePhoto petProfilePhoto = PetProfilePhoto.toPetProfilePhoto(photoData, registerPet);
                petPhotoRepository.save(petProfilePhoto);
            }
        }

        return PetDto.Response.Register.toDto(registerPet.getPetName());
    }

    @Override
    public void petModify(Long petId, List<MultipartFile> petProfileImgs, PetDto.Request.Register petRegisterDto, Long loginMemberId) {
        Pet pet = petRepository.findById(petId).orElseThrow(() -> new CustomException(CustomResponseStatus.PET_NOT_FOUND));
        pet.modify(petRegisterDto);

        for (PetProfilePhoto petProfilePhoto : pet.getPetProfilePhotos()) {
            s3Util.deleteFile(petProfilePhoto.getPhotoData().getBucket(), petProfilePhoto.getPhotoData().getObject());
            petPhotoRepository.delete(petProfilePhoto);
        }

        if (!petProfileImgs.isEmpty()) {
            for (MultipartFile petProfileImg : petProfileImgs) {
                PhotoData photoData = s3Util.uploadFile(petProfileImg);
                PetProfilePhoto petProfilePhoto = PetProfilePhoto.toPetProfilePhoto(photoData, pet);
                petPhotoRepository.save(petProfilePhoto);
            }
        }
    }

    @Override
    public void petDelete(Long petId) {
        petRepository.deleteById(petId);
    }

    @Override
    public PetDto.Response.PetDetailInfo fetchPetDetailInfo(Long petId) {
        Pet pet = petRepository.findById(petId).orElseThrow(() -> new CustomException(CustomResponseStatus.PET_NOT_FOUND));

        return new PetDto.Response.PetDetailInfo(pet);
    }
}
