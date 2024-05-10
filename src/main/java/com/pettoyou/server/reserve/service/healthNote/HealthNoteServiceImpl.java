package com.pettoyou.server.reserve.service.healthNote;

import com.pettoyou.server.constant.enums.CustomResponseStatus;
import com.pettoyou.server.constant.exception.CustomException;
import com.pettoyou.server.pet.entity.Pet;
import com.pettoyou.server.pet.repository.PetRepository;
import com.pettoyou.server.reserve.dto.HealthNoteDto;
import com.pettoyou.server.reserve.entity.HealthNote;
import com.pettoyou.server.reserve.repository.HealthNoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HealthNoteServiceImpl implements HealthNoteService {
    private final HealthNoteRepository healthNoteRepository;
    private final PetRepository petRepository;
    @Override
    public void register(HealthNoteDto.Request.Register registerDto) {
        Pet pet = petRepository.findById(registerDto.petId())
                .orElseThrow(() -> new CustomException(CustomResponseStatus.PET_NOT_FOUND));

        HealthNote entity = HealthNote.toEntity(registerDto, pet);
        healthNoteRepository.save(entity);
    }
}
