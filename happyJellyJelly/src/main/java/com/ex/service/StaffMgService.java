package com.ex.service;
import com.ex.data.BranchesDTO;
import com.ex.data.StaffMgDTO;
import com.ex.entity.BranchEntity;
import com.ex.entity.MembersEntity;
import com.ex.repository.BranchesRepository;
import com.ex.repository.MembersMgRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StaffMgService {

    private final MembersMgRepository membersMgRepository;
    private final BranchesRepository branchRepository;

    // 직원 조회 관련 메서드
    public Page<StaffMgDTO> getAllStaff(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<MembersEntity> staffEntities = membersMgRepository.findByUserTypeNotIn(List.of("REGULAR"), pageable);
        return staffEntities.map(this::convertToDTOWithBranch);
    }

    public List<StaffMgDTO> getAllStaffList() {
        List<MembersEntity> staffEntities = membersMgRepository.findByUserTypeNotIn(List.of("REGULAR"));
        return staffEntities.stream()
                            .map(this::convertToDTOWithBranch)
                            .collect(Collectors.toList());
    }
    
    public StaffMgDTO getStaffById(Integer id) {
        return membersMgRepository.findById(id)
//            .filter(member -> isStaff(member.getUserType()))
//            .map(this::convertToDTOWithBranch)
//            .orElse(null);
//    }
            .map(this::convertToDTOWithBranch)
            .orElseThrow(() -> new RuntimeException("Staff not found"));
    }

    public Page<StaffMgDTO> searchStaff(String keyword, int page, int size, String sortBy, String sortDir) {
        Sort sort = Sort.by(sortDir.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<MembersEntity> staffPage = membersMgRepository.searchStaffByKeyword(keyword, pageable);

        return staffPage.map(this::convertToDTOWithBranch);
    }

    public List<StaffMgDTO> getAllRegularMembers() {
        return membersMgRepository.findByUserType("REGULAR")
            .stream()
            .map(this::convertToDTOWithBranch)
            .collect(Collectors.toList());
    }

    // 직원 관리 관련 메서드
//    @Transactional
//    public StaffMgDTO registerStaff(StaffMgDTO staffDTO) {
//        MembersEntity member = membersMgRepository.findByMemberId(staffDTO.getMemberId())
//                .orElseThrow(() -> new RuntimeException("Member not found"));
//        
//        if (!isValidUserType(staffDTO.getUsertype())) {
//            throw new IllegalArgumentException("Invalid user type");
//        }
//
//        member.setUserType(staffDTO.getUsertype());
//        member.setBranchId(staffDTO.getBranchId());
//        
//
//        MembersEntity savedMember = membersMgRepository.save(member);
//        return convertToDTOWithBranch(savedMember);
//    }

    @Transactional
    public StaffMgDTO registerStaff(StaffMgDTO staffDTO) {
        MembersEntity member = membersMgRepository.findByMemberId(staffDTO.getMemberId())
                .orElseThrow(() -> new RuntimeException("Member not found"));

        if (!isValidUserType(staffDTO.getUsertype())) {
            throw new IllegalArgumentException("Invalid user type");
        }

        // 회원 정보를 직원 정보로 업데이트
        member.setUserType(staffDTO.getUsertype());
        member.setBranchId(staffDTO.getBranchId());

        MembersEntity savedMember = membersMgRepository.save(member);

        // DTO 변환 시 active 상태 설정
        StaffMgDTO savedStaffDTO = convertToDTOWithBranch(savedMember);
        savedStaffDTO.setActive(true); // 새로 등록된 직원은 기본적으로 활성 상태

        return savedStaffDTO;
    }
    
//    @Transactional
//    public StaffMgDTO updateStaff(Integer id, StaffMgDTO staffDTO) {
//        MembersEntity member = membersMgRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Staff not found"));
//
//        if (!staffDTO.isActive()) {
//            // 퇴사 처리
//            member.setUserType("REGULAR");
//            member.setBranchId(null);
//        } else {
//            // 재직 중 처리
//            member.setUserType(staffDTO.getUsertype());
//            member.setBranchId(staffDTO.getBranchId());
//        }
//
//        MembersEntity updatedMember = membersMgRepository.save(member);
//        return convertToDTOWithBranch(updatedMember);
//    }

    public StaffMgDTO updateStaff(Integer id, StaffMgDTO staffDTO) {
        MembersEntity member = membersMgRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Staff not found"));

        if (!staffDTO.isActive()) {
            // 퇴사 처리
            member.setUserType("REGULAR");
            member.setBranchId(null);
        } else {
            // 재직 중 처리
            if (isValidUserType(staffDTO.getUsertype())) {
                member.setUserType(staffDTO.getUsertype());
            } else {
                throw new IllegalArgumentException("Invalid user type");
            }
            member.setBranchId(staffDTO.getBranchId());
        }

        MembersEntity updatedMember = membersMgRepository.save(member);
        return convertToDTOWithBranch(updatedMember);
    }
    
    @Transactional
    public StaffMgDTO updateStaffType(Integer id, String newType) {
        MembersEntity member = membersMgRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Member not found"));
        
        if (!isValidUserType(newType)) {
            throw new IllegalArgumentException("Invalid user type");
        }

        member.setUserType(newType);
        MembersEntity updatedMember = membersMgRepository.save(member);
        return convertToDTOWithBranch(updatedMember);
    }

    @Transactional
    public Boolean deactivateStaff(Integer id) {
        MembersEntity member = membersMgRepository.findById(id).orElse(null);
        if (member != null && isStaff(member.getUserType())) {
            member.setUserType("INACTIVE");
            membersMgRepository.save(member);
            return true;
        }
        return false;
    }

    @Transactional
    public Boolean deleteStaff(Integer id) {
        if (membersMgRepository.existsById(id)) {
            MembersEntity member = membersMgRepository.findById(id).orElse(null);
            if (member != null && isStaff(member.getUserType())) {
                membersMgRepository.deleteById(id);
                return true;
            }
        }
        return false;
    }

    // 지점 관련 메서드
    public List<BranchesDTO> getAllBranches() {
        return branchRepository.findAll().stream()
                .map(this::convertToBranchDTO)
                .collect(Collectors.toList());
    }

    // 유틸리티 메서드
    private Boolean isStaff(MembersEntity member) {
        return isStaff(member.getUserType());
    }

    private Boolean isStaff(String userType) {
        return List.of("TEACHER", "DIRECTOR", "ADMIN").contains(userType);
    }

    private Boolean isValidUserType(String userType) {
        return List.of("REGULAR", "TEACHER", "DIRECTOR", "ADMIN", "INACTIVE").contains(userType);
    }

    private StaffMgDTO convertToDTOWithBranch(MembersEntity member) {
        StaffMgDTO dto = StaffMgDTO.builder()
            .memberId(member.getMemberId())
            .username(member.getUsername())
            .name(member.getName())
            .email(member.getEmail())
            .phone(member.getPhone())
            .usertype(member.getUserType())
            .branchId(member.getBranchId())
            .active(!List.of("REGULAR", "INACTIVE").contains(member.getUserType()))
            .build();

        if (member.getBranchId() != null) {
            BranchEntity branch = branchRepository.findById(member.getBranchId())
                .orElse(null);
            if (branch != null) {
                dto.setBranchName(branch.getName());
            }
        }
        return dto;
    }

    private BranchesDTO convertToBranchDTO(BranchEntity branch) {
        return BranchesDTO.builder()
                .branchId(branch.getBranchId())
                .branchesName(branch.getName())
                .address(branch.getAddress())
                .address2(branch.getAddress2())
                .phone(branch.getPhone())
                .active(branch.getActive())
                .build();
    }
}