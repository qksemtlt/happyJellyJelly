package com.ex.service;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.ex.data.AdmissionsDTO;
import com.ex.data.MonthcareGroupsDTO;
import com.ex.entity.AdmissionsEntity;
import com.ex.entity.BranchEntity;
import com.ex.entity.DogsEntity;
import com.ex.entity.MembersEntity;
import com.ex.entity.MonthcareGroupsEntity;
import com.ex.repository.AdmissionsRepository;
import com.ex.repository.BranchesRepository;
import com.ex.repository.DogsRepository;
import com.ex.repository.MembersRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class AdmissionsService {
    private final AdmissionsRepository admissionRepository;
    private final DogsRepository dogRepository;
    private final BranchesRepository branchRepository;
    private final MonthcareGroupsService monthcareGroupsService;
    private final MembersRepository membersRepository;
   
    public void createAdmission(AdmissionsDTO admissionDTO) {
        DogsEntity dog = dogRepository.findById(admissionDTO.getDogs().getDogId())
                .orElseThrow(() -> new RuntimeException("Dog not found"));        
        BranchEntity branch = branchRepository.findById(admissionDTO.getBranch().getBranchId())
                .orElseThrow(() -> new RuntimeException("Branch not found"));
        MonthcareGroupsDTO groupDTO = monthcareGroupsService.getMonthGroup(admissionDTO.getMothcaregroups().getId());

        AdmissionsEntity ae = AdmissionsEntity.builder()
                .dogs(dog)       
                .applicationDate(new Date())
                .status("PENDING")
                .pottytraining(admissionDTO.getPottytraining())
                .marking(admissionDTO.getMarking())
                .ration(admissionDTO.getRation())
                .appetite(admissionDTO.getAppetite())
                .walk(admissionDTO.getWalk())
                .numberofweeks(admissionDTO.getNumberofweeks())
                .significant(admissionDTO.getSignificant())
                .branch(branch)
                .monthcaregroups(admissionDTO.getMothcaregroups())
                .build();
        log.info("Created AdmissionsEntity: {}", ae);

        try {
            AdmissionsEntity savedEntity = admissionRepository.save(ae);
            log.info("Successfully saved admission: {}", savedEntity);
        } catch (Exception e) {
            log.error("Error while saving admission", e);
            throw e;
        }
    }
     
  
    
    public AdmissionsDTO getAdmissionById(Integer id) {
        return admissionRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    private AdmissionsDTO convertToDTO(AdmissionsEntity entity) {
        AdmissionsDTO dto = new AdmissionsDTO();
        dto.setAdmissionId(entity.getAdmissionId());
        dto.setDogs(entity.getDogs());
        dto.setApplicationDate(entity.getApplicationDate());
        dto.setStatus(entity.getStatus());
        dto.setApprovalDate(entity.getApprovalDate());
        dto.setPottytraining(entity.getPottytraining());
        dto.setMarking(entity.getMarking());
        dto.setRation(entity.getRation());
        dto.setAppetite(entity.getAppetite());
        dto.setWalk(entity.getWalk());
        dto.setNumberofweeks(entity.getNumberofweeks());
        dto.setSignificant(entity.getSignificant());
        dto.setReason(entity.getReason());
        dto.setBranch(entity.getBranch());
        dto.setMothcaregroups(entity.getMonthcaregroups());
        return dto;
    }


    public void updateAdmissionStatus(Integer admissionId, String newStatus, String reason) {
        AdmissionsEntity admission = admissionRepository.findById(admissionId)
                .orElseThrow(() -> new RuntimeException("Admission not found"));

        admission.setStatus(newStatus);

        if ("APPROVED".equals(newStatus)) {
            admission.setApprovalDate(new Date());
        } else {
            admission.setApprovalDate(null);
            admission.setReason(reason);
        }
        admissionRepository.save(admission);
    }
    
 
    public void cancelAdmission(Integer id) {
        AdmissionsEntity admission = admissionRepository.findById(id).get();
        admission.setStatus("CANCELED");
        admissionRepository.save(admission);
     }
    public Page<AdmissionsDTO> getAllAdmissionsPaginated(int page) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by("admissionId").descending());
        Page<AdmissionsEntity> entityPage = admissionRepository.findAll(pageable);
        return entityPage.map(this::convertToDTO);
    }

    public Page<AdmissionsDTO> getAdmissionsByUsernamePaginated(String username, int page) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by("admissionId").descending());
        Page<AdmissionsEntity> entityPage = admissionRepository.findByDogs_Member_Username(username, pageable);
        return entityPage.map(this::convertToDTO);
    }
    
    public int checkPending(String status, Integer dog_id) {
        return admissionRepository.countByStatusAndDogs_DogId(status, dog_id);
    }

    public List<BranchEntity> getAllBranches() {
        return branchRepository.findAll();
    }

    public List<MonthcareGroupsDTO> getGroupsByBranch(Integer branchId) {
        return monthcareGroupsService.getMonthcareGroupByBranch(branchId);
    }
    
   
    // 지점 관리자용 메서드
    public Page<AdmissionsDTO> getAdmissionsByBranchPaginated(Integer branchId, int page) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by("admissionId").descending());
        Page<AdmissionsEntity> entityPage = admissionRepository.findByBranch_BranchId(branchId, pageable);
        return entityPage.map(this::convertToDTO);
    }
    
    
    public Page<AdmissionsDTO> getAdmissionsByRole(String username, int page) {
        if (username.startsWith("admin_")) {
            return getAllAdmissionsPaginated(page);
        } else if (username.startsWith("director_")) {
            MembersEntity member = membersRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Member not found"));
            return getAdmissionsByBranchPaginated(member.getBranchId(), page);
        } else {
            return getAdmissionsByUsernamePaginated(username, page);
        }
    }
    }
    
