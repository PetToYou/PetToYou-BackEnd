package com.pettoyou.server.pet.service;

import com.pettoyou.server.constant.enums.CustomResponseStatus;
import com.pettoyou.server.constant.exception.CustomException;
import com.pettoyou.server.member.entity.Member;
import com.pettoyou.server.member.repository.MemberRepository;
import com.pettoyou.server.pet.dto.PetDto;
import com.pettoyou.server.pet.entity.Pet;
import com.pettoyou.server.pet.entity.PetMedicalInfo;
import com.pettoyou.server.pet.entity.PetProfilePhoto;
import com.pettoyou.server.pet.repository.PetPhotoRepository;
import com.pettoyou.server.pet.repository.PetRepository;
import com.pettoyou.server.util.S3Util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PetServiceImpl implements PetService {
    private final S3Util s3Util;
    private final PetRepository petRepository;
    private final PetPhotoRepository petPhotoRepository;
    private final MemberRepository memberRepository;

    @Override
    public PetDto.Response.Register petRegister(List<MultipartFile> petProfileImgs, PetDto.Request.Register petRegisterDto, String loginUsername) {
        Member member = memberRepository.findByName(loginUsername).orElseThrow(() -> {
            throw new CustomException(CustomResponseStatus.MEMBER_NOT_FOUND);
        });

        Pet registerPet = Pet.toEntity(petRegisterDto, member);

        petRepository.save(registerPet);

        if (!petProfileImgs.isEmpty()) {
            List<String> photoUrls = s3Util.uploadFile(petProfileImgs);
            photoUrls.forEach(photoUrl ->
                    petPhotoRepository.save(PetProfilePhoto.toPetProfilePhoto(photoUrl, registerPet))
            );
        }

        return PetDto.Response.Register.toDto(registerPet.getPetName());
    }
}
