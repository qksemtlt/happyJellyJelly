package com.ex.service;
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

@Service // 이 클래스가 Spring의 서비스 계층 컴포넌트임을 나타냅니다.
@RequiredArgsConstructor // Lombok 어노테이션으로, final 필드에 대한 생성자를 자동으로 생성합니다.
public class DogAssignmentsService {
    // 필요한 레포지토리들을 주입받습니다.
    private final DogAssignmentsRepository dogAssignmentsRepository;
    private final AdmissionsRepository admissionsRepository;
    private final MonthcareGroupsRepository monthcareGroupsRepository;

    @Transactional // 이 메소드가 트랜잭션으로 실행되어야 함을 나타냅니다.
    public void assignDogToClass(Integer admissionId) {
        // 주어진 ID로 입학 정보를 조회합니다. 없으면 예외를 던집니다.
        AdmissionsEntity admission = admissionsRepository.findById(admissionId)
            .orElseThrow(() -> new RuntimeException("Admission not found"));
        
        // 입학 상태가 'DONE'이 아니면 예외를 던집니다.
        if (!"DONE".equals(admission.getStatus())) {
            throw new RuntimeException("Payment not completed for this admission");
        }

        // 새로운 강아지 배정 엔티티를 생성합니다.
        DogAssignmentsEntity assignment = new DogAssignmentsEntity();
        assignment.setDogs(admission.getDogs());  // 강아지 정보를 설정합니다.
        assignment.setMonthgroup(admission.getMonthcaregroups());  // 월간 그룹을 설정합니다.
        assignment.setStartDate(admission.getSubscription().getStartDate());  // 시작 날짜를 현재로 설정합니다.
        assignment.setEndDate(admission.getSubscription().getEndDate()); // 종료 날짜를 1달 후로 설정합니다.
        assignment.setAdmission(admission);  // 입학 정보를 설정합니다.

        // 생성한 배정 정보를 저장합니다.
        dogAssignmentsRepository.save(assignment);
    }

    // 특정 그룹의 모든 강아지 배정 정보를 조회합니다.
    public List<DogAssignmentsDTO> getAssignmentsByGroup(Integer groupId) {
        // 주어진 ID로 월간 그룹을 조회합니다. 없으면 예외를 던집니다.
        MonthcareGroupsEntity monthgroup = monthcareGroupsRepository.findById(groupId)
            .orElseThrow(() -> new RuntimeException("Monthcare group not found"));

        // 해당 그룹의 모든 배정 정보를 조회합니다.
        List<DogAssignmentsEntity> assignments = dogAssignmentsRepository.findByMonthgroup(monthgroup);

        // 엔티티를 DTO로 변환하여 반환합니다.
        return assignments.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    // 엔티티를 DTO로 변환하는 private 메소드입니다.
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
    
    public int countCurrentStudentsInGroup(Integer groupId) {
        List<DogAssignmentsDTO> assignments = getAssignmentsByGroup(groupId);
        System.out.println("Group ID: " + groupId + ", Assignments DTO count: " + assignments.size());
        return assignments.size();
    }
}