package com.ex.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import com.ex.data.AdmissionsDTO;
import com.ex.entity.AdmissionsEntity;
import com.ex.entity.DogsEntity;
import com.ex.repository.AdmissionsRepository;
import com.ex.repository.dogsRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AdmissionsService {
    private final AdmissionsRepository admissionRepository;
    private final dogsRepository dogRepository;

    public void createAdmission(AdmissionsDTO admissionDTO) {
        DogsEntity dog = dogRepository.findById(admissionDTO.getDogId())
                .orElseThrow(() -> new RuntimeException("Dog not found"));

        AdmissionsEntity ae = AdmissionsEntity.builder()
                .dogs(dog)
                .applicationDate(admissionDTO.getApplicationDate())
                .status(admissionDTO.getStatus())
                .approvalDate(admissionDTO.getApprovalDate())
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

    public AdmissionsDTO getAdmissionById(Integer id) {
        return admissionRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    private AdmissionsDTO convertToDTO(AdmissionsEntity entity) {
        AdmissionsDTO dto = new AdmissionsDTO();
        dto.setAdmissionId(entity.getAdmissionId());
        dto.setDogId(entity.getDogs() != null ? entity.getDogs().getDog_id() : null);
        dto.setDogname(entity.getDogs() != null ? entity.getDogs().getDogname() : null);  // 추가된 라인
        dto.setApplicationDate(entity.getApplicationDate());
        dto.setStatus(entity.getStatus());
        dto.setApprovalDate(entity.getApprovalDate());
        dto.setDesiredSubsType(entity.getDesiredSubsType());
        dto.setDesiredUsageCount(entity.getDesiredUsageCount());
        dto.setDesiredDaysPerWeek(entity.getDesiredDaysPerWeek());
        return dto;
    }


    public AdmissionsDTO updateAdmissionStatus(Integer admissionId, String newStatus) {
        AdmissionsEntity admission = admissionRepository.findById(admissionId)
                .orElseThrow(() -> new RuntimeException("Admission not found"));

        admission.setStatus(newStatus);

        if ("REJECTED".equals(newStatus)) {
            admission.setApprovalDate(new Date());
        } else {
            admission.setApprovalDate(null);
        }

        AdmissionsEntity updatedAdmission = admissionRepository.save(admission);
        return convertToDTO(updatedAdmission);
    }
}