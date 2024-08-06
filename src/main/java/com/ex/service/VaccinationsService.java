package com.ex.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import com.ex.data.VaccinationsDTO;
import com.ex.entity.VaccinationsEntity;
import com.ex.repository.VaccinationsRepository;
import com.ex.repository.DogsRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VaccinationsService {
    private final VaccinationsRepository vaccinationsRepository;
    private final DogsRepository dogsRepository;

    public void saveVaccination(VaccinationsDTO vaccinationDTO) {
        VaccinationsEntity vaccination = convertToEntity(vaccinationDTO);
        vaccinationsRepository.save(vaccination);
    }

    private VaccinationsEntity convertToEntity(VaccinationsDTO dto) {
        return VaccinationsEntity.builder()
            .dogs(dto.getDogs())
            .vaccine_type(dto.getVaccine_type())
            .vaccination_date(dto.getVaccination_date())
            .expiry_date(dto.getExpiry_date())
            .filename(dto.getFilename())
            .build();
    }
    
    public List<VaccinationsEntity> getVaccinationsByDogId(Integer dogId){
    	return vaccinationsRepository.findAll().stream()
    			.filter(vaccination -> vaccination.getDogs().getDog_id().equals(dogId))
                .collect(Collectors.toList());
    }
}