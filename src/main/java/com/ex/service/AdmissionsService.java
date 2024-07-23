package com.ex.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import com.ex.data.AdmissionsDTO;
import com.ex.entity.AdmissionsEntity;
import com.ex.repository.AdmissionsRepository;


import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@Service
public class AdmissionsService {
    private final AdmissionsRepository admissionRepository;

    public void createAdmission(AdmissionsDTO admissionDTO) {
        AdmissionsEntity ae = AdmissionsEntity.builder()
                .dogId(admissionDTO.getDogId())
                .applicationDate(admissionDTO.getApplicationDate())
                .status(admissionDTO.getStatus())
                .approvalDate(admissionDTO.getApplicationDate())
                .desiredSubsType(admissionDTO.getDesiredSubsType())
                .desiredUsageCount(admissionDTO.getDesiredUsageCount())
                .desiredDaysPerWeek(admissionDTO.getDesiredDaysPerWeek())
                .build();
        admissionRepository.save(ae);
    }

    public List<AdmissionsDTO> getAllAdmissions() {
        return admissionRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public AdmissionsDTO getAdmissionById(Long id) {
        return admissionRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    public List<AdmissionsDTO> getAdmissionsByDogId(Long dogId) {
        return admissionRepository.findByDogId(dogId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private AdmissionsDTO convertToDTO(AdmissionsEntity entity) {
        AdmissionsDTO dto = new AdmissionsDTO();
        dto.setAdmissionId(entity.getAdmissionId());
        dto.setDogId(entity.getDogId());
        dto.setApplicationDate(entity.getApplicationDate());
        dto.setStatus(entity.getStatus());
        dto.setApprovalDate(entity.getApprovalDate());
        dto.setDesiredSubsType(entity.getDesiredSubsType());
        dto.setDesiredUsageCount(entity.getDesiredUsageCount());
        dto.setDesiredDaysPerWeek(entity.getDesiredDaysPerWeek());
        return dto;
    }
}