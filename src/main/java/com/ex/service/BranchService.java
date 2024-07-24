package com.ex.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ex.data.BranchDTO;
import com.ex.data.BranchListResponseDTO;
import com.ex.entity.BranchEntity;
import com.ex.repository.BranchRepository;
import com.ex.repository.MembersRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BranchService {
    private final BranchRepository branchRepository;
    private final MembersRepository memberRepository;
    /**
     * 새로운 지점을 등록합니다.
     * @param branchDTO 등록할 지점 정보
     * @return 등록된 지점 정보
     */
    @Transactional
    public BranchDTO registerBranch(BranchDTO branchDTO) {
        BranchEntity branch = new BranchEntity();
        branch.setName(branchDTO.getName());
        branch.setAddress(branchDTO.getAddress());
        branch.setPhone(branchDTO.getPhone());
        branch.setManager_id(branchDTO.getManager_id());
        branch.setActive(true); // 새로 등록된 매장은 기본적으로 활성 상태

        BranchEntity savedBranch = branchRepository.save(branch);
        return convertToDTO(savedBranch);
    }
    
    @Transactional
    public BranchDTO updateBranch(BranchDTO branchDTO) {
        BranchEntity branch = branchRepository.findById(branchDTO.getBranch_id())
                .orElseThrow(() -> new EntityNotFoundException("Branch not found with id: " + branchDTO.getBranch_id()));

        branch.setName(branchDTO.getName());
        branch.setAddress(branchDTO.getAddress());
        branch.setPhone(branchDTO.getPhone());
        branch.setManager_id(branchDTO.getManager_id());

        BranchEntity updatedBranch = branchRepository.save(branch);
        return convertToDTO(updatedBranch);
    }

    /**
     * 페이지네이션된 지점 목록을 조회합니다.
     * @param page 페이지 번호
     * @param size 페이지 크기
     * @return 지점 목록과 페이지 정보를 포함한 응답 객체
     */
    public BranchListResponseDTO listBranches(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<BranchEntity> branchPage = branchRepository.findAll(pageRequest);
        List<BranchDTO> branchDTOs = branchPage.getContent().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        return BranchListResponseDTO.builder()
                .branches(branchDTOs)
                .totalPages(branchPage.getTotalPages())
                .totalElements(branchPage.getTotalElements())
                .build();
    }

    /**
     * 모든 지점 목록을 조회합니다.
     * @return 모든 지점의 DTO 리스트를 포함한 응답 객체
     */
    public BranchListResponseDTO listAllBranches() {
        List<BranchEntity> branches = branchRepository.findAll();
        System.out.println("Found " + branches.size() + " branches in the database");
        List<BranchDTO> branchDTOs = branches.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        

        return BranchListResponseDTO.builder()
                .branches(branchDTOs)
                .totalElements(branches.size())
                .totalPages(1) // 페이지네이션을 사용하지 않는 경우 1로 설정
                .build();
    }

    /**
     * 지점의 활성 상태를 토글합니다.
     * @param id 상태를 변경할 지점의 ID
     * @return 업데이트된 지점 정보
     */
    @Transactional
    public BranchDTO toggleBranchStatus(Integer id) {
        BranchEntity branch = branchRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Branch not found with id: " + id));
        branch.setActive(!branch.isActive());
        BranchEntity savedBranch = branchRepository.save(branch);
        return convertToDTO(savedBranch);
    }

    /**
     * 지점을 삭제합니다.
     * @param id 삭제할 지점의 ID
     */
    @Transactional
    public void deleteBranch(Integer id) {
        if (!branchRepository.existsById(id)) {
            throw new EntityNotFoundException("Branch not found with id: " + id);
        }
        branchRepository.deleteById(id);
    }

    /**
     * DTO를 Entity로 변환합니다.
     * @param branchDTO 변환할 DTO
     * @return 변환된 Entity
     */
    private BranchEntity convertToEntity(BranchDTO branchDTO) {
        return BranchEntity.builder()
                .branch_id(branchDTO.getBranch_id())
                .name(branchDTO.getName())
                .address(branchDTO.getAddress())
                .phone(branchDTO.getPhone())
                .manager_id(branchDTO.getManager_id())
                .active(branchDTO.isActive())
                .build();
    }

    /**
     * Entity를 DTO로 변환합니다.
     * @param branch 변환할 Entity
     * @return 변환된 DTO
     */
    private BranchDTO convertToDTO(BranchEntity branch) {
        return BranchDTO.builder()
                .branch_id(branch.getBranch_id())
                .name(branch.getName())
                .address(branch.getAddress())
                .phone(branch.getPhone())
                .manager_id(branch.getManager_id())
                .active(branch.isActive())
                .build();
    }
    
    //@Transactional(readOnly = true)
    public BranchDTO getBranchById(Integer id) {
        BranchEntity branch = branchRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Branch not found with id: " + id));
        return convertToDTO(branch);
    }
    
    
}