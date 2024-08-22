package com.ex.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ex.data.DogAssignmentsDTO;
import com.ex.data.MonthcareGroupsDTO;
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
    private final MonthcareGroupsService monthcareGroupsService;

    @Transactional
    public void assignDogToClass(Integer admissionId) {
        AdmissionsEntity admission = admissionsRepository.findById(admissionId)
            .orElseThrow(() -> new RuntimeException("Admission not found"));

        LocalDate currentDate = LocalDate.now();

        if (!"DONE".equals(admission.getStatus())) {
            throw new RuntimeException("Payment not completed for this admission");
        }

        LocalDate startDate = admission.getSubscription().getStartDate();
        if (startDate.isBefore(currentDate)) {
            throw new RuntimeException("Cannot assign dog to class for past dates");
        }

        DogAssignmentsEntity assignment = new DogAssignmentsEntity();
        assignment.setDogs(admission.getDogs());
        assignment.setMonthgroup(admission.getMonthcaregroups());
        assignment.setStartDate(startDate);
        assignment.setEndDate(admission.getSubscription().getEndDate());
        assignment.setAdmission(admission);

        dogAssignmentsRepository.save(assignment);
    }

    public List<DogAssignmentsDTO> getCurrentAndFutureAssignmentsByGroup(Integer groupId) {
        MonthcareGroupsEntity monthgroup = monthcareGroupsRepository.findById(groupId)
            .orElseThrow(() -> new RuntimeException("Monthcare group not found"));

        LocalDate today = LocalDate.now();

        List<DogAssignmentsEntity> assignments = dogAssignmentsRepository.findByMonthgroup(monthgroup);

        return assignments.stream()
            .filter(assignment -> !assignment.getEndDate().isBefore(today))
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

    public int countCurrentAndFutureStudentsInGroup(Integer groupId) {
        return getCurrentAndFutureAssignmentsByGroup(groupId).size();
    }

    public Map<Integer, List<DogAssignmentsDTO>> getCurrentAndFutureAssignmentsByBranch(Integer branchId) {
        List<MonthcareGroupsDTO> groups = monthcareGroupsService.getMonthcareGroupByBranch(branchId);
        Map<Integer, List<DogAssignmentsDTO>> assignmentsByGroup = new HashMap<>();

        for (MonthcareGroupsDTO group : groups) {
            List<DogAssignmentsDTO> assignments = getCurrentAndFutureAssignmentsByGroup(group.getId());
            assignmentsByGroup.put(group.getId(), assignments);
        }

        return assignmentsByGroup;
    }

    public Map<Integer, Integer> countCurrentAndFutureStudentsByBranch(Integer branchId) {
        List<MonthcareGroupsDTO> groups = monthcareGroupsService.getMonthcareGroupByBranch(branchId);
        Map<Integer, Integer> studentCountByGroup = new HashMap<>();

        for (MonthcareGroupsDTO group : groups) {
            int studentCount = countCurrentAndFutureStudentsInGroup(group.getId());
            studentCountByGroup.put(group.getId(), studentCount);
        }

        return studentCountByGroup;
    }

    public Map<String, Object> getAssignmentsInfoByBranch(Integer branchId) {
        Map<String, Object> result = new HashMap<>();
        List<MonthcareGroupsDTO> groups = monthcareGroupsService.getMonthcareGroupByBranch(branchId);
        Map<Integer, List<DogAssignmentsDTO>> assignmentsByGroup = getCurrentAndFutureAssignmentsByBranch(branchId);
        Map<Integer, Integer> studentCountByGroup = countCurrentAndFutureStudentsByBranch(branchId);

        result.put("groups", groups);
        result.put("assignmentsByGroup", assignmentsByGroup);
        result.put("studentCountByGroup", studentCountByGroup);

        return result;
    }
}