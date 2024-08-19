package com.ex.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ex.data.DogAssignmentsDTO;
import com.ex.entity.AdmissionsEntity;
import com.ex.entity.DogAssignmentsEntity;
import com.ex.entity.MonthcareGroupsEntity;
import com.ex.repository.AdmissionsRepository;
import com.ex.repository.DogAssignmentsRepository;
import com.ex.repository.MonthcareGroupsRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DogAssignmentsService {
    private final DogAssignmentsRepository dogAssignmentsRepository;
    private final AdmissionsRepository admissionsRepository;
    private final MonthcareGroupsRepository monthcareGroupsRepository;

    @Transactional
    public void assignDogToClass(Integer admissionId) {
        AdmissionsEntity admission = admissionsRepository.findById(admissionId)
            .orElseThrow(() -> new RuntimeException("Admission not found"));
        
        if (!"DONE".equals(admission.getStatus())) {
            throw new RuntimeException("Payment not completed for this admission");
        }

        DogAssignmentsEntity assignment = new DogAssignmentsEntity();
        assignment.setDogs(admission.getDogs());  // 'setDogs'에서 'setDog'로 변경
        assignment.setMonthgroup(admission.getMonthcaregroups());
        assignment.setStartDate(LocalDate.now());
        assignment.setEndDate(LocalDate.now().plusMonths(1)); // Assuming 1-month assignment
        assignment.setAdmission(admission);

        dogAssignmentsRepository.save(assignment);
    }

    public List<DogAssignmentsDTO> getAssignmentsByGroup(Integer groupId) {
        MonthcareGroupsEntity monthgroup = monthcareGroupsRepository.findById(groupId)
            .orElseThrow(() -> new RuntimeException("Monthcare group not found"));
        List<DogAssignmentsEntity> assignments = dogAssignmentsRepository.findByMonthgroup(monthgroup);
        return assignments.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    private DogAssignmentsDTO convertToDTO(DogAssignmentsEntity entity) {
        return DogAssignmentsDTO.builder()
            .dogassignmentId(entity.getDogassignmentId())
            .dogs(entity.getDogs())
            .monthgroup(entity.getMonthgroup())
            .startDate(entity.getStartDate())
            .endDate(entity.getEndDate())
            .admission(entity.getAdmission())
            .build();
    }
}