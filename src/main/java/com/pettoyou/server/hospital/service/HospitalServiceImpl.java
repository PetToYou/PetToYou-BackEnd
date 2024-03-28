package com.pettoyou.server.hospital.service;

import com.pettoyou.server.hospital.interfaces.ContainInterface;
import com.pettoyou.server.hospital.dto.HospitalDto;
import com.pettoyou.server.hospital.entity.Hospital;
import com.pettoyou.server.hospital.repository.HospitalRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HospitalServiceImpl implements HospitalService{

    private final HospitalRepository hospitalRepository;

    public HospitalServiceImpl(HospitalRepository hospitalRepository) {
        this.hospitalRepository = hospitalRepository;
    }

    @Override
    public List<Hospital> getAllHospList(){

        List<Hospital> allHospitals = hospitalRepository.findAll();
        return allHospitals;
    }

    //위치기반 근처 병원 조회 (일반)
    @Override
    public List<HospitalDto.Test> getHospitalsContain(HospitalDto.Request location) {

        String point = location.toPointString();
        Integer radius = location.getRadius();


        List<ContainInterface> hospitals =  hospitalRepository.findHospitalsContain(point, radius);

        List<HospitalDto.Test> result = hospitals.stream().map(h -> new HospitalDto.Test(h.getStoreId(), h.getHospitalName(), h.getThumbnailUrl())).collect(Collectors.toList());
//, h.getDistance()

        return result;

    }


}
