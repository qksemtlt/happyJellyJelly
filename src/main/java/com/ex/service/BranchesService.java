package com.ex.service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ex.data.BranchesDTO;
import com.ex.data.BranchesListResponseDTO;
import com.ex.entity.BranchEntity;
import com.ex.repository.BranchesRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BranchesService {
    private final BranchesRepository branchesRepository;

    //새로운 지점을 등록합니다.
    @Transactional
    public BranchesDTO registerBranch(BranchesDTO branchesDTO) {
        BranchEntity branch = new BranchEntity();
        branch.setName(branchesDTO.getBranchesName());
        branch.setAddress(branchesDTO.getAddress());
        branch.setPhone(branchesDTO.getPhone());
        branch.setActive(true); // 새로 등록된 매장은 기본적으로 활성 상태

        BranchEntity savedBranch = branchesRepository.save(branch);
        return convertToDTO(savedBranch);
    }

    // 모든 지점을 반환하는 메서드
    public List<BranchesDTO> getAllBranches() {
        List<BranchEntity> branchEntities = branchesRepository.findAll();
        return branchEntities.stream()
                             .map(this::convertToDTO)
                             .collect(Collectors.toList());
    }


    // 키워드로 지점을 검색하는 메서드
    public List<BranchesDTO> searchBranches(String keyword) {
        List<BranchEntity> branchEntities = branchesRepository.findByNameContainingIgnoreCaseOrAddressContainingIgnoreCase(keyword, keyword);
        return branchEntities.stream()
                             .map(this::convertToDTO)
                             .collect(Collectors.toList());
    }
    
    @Transactional
    public void updateBranch(Integer id, BranchesDTO branchesDTO) {
    	Optional<BranchEntity> op = branchesRepository.findById(id);
    	if(op.isPresent()) {
    		BranchEntity be = op.get();
    		be.setName(branchesDTO.getBranchesName());
    		be.setAddress(branchesDTO.getAddress());
    		be.setPhone(branchesDTO.getPhone());
    		branchesRepository.save(be);
    	}
    }
    
    //페이지네이션된 지점 목록을 조회합니다.
    public BranchesListResponseDTO listBranches(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<BranchEntity> branchPage = branchesRepository.findAll(pageRequest);
        List<BranchesDTO> branchesDTOs = branchPage.getContent().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        return BranchesListResponseDTO.builder()
                .branches(branchesDTOs)
                .totalPages(branchPage.getTotalPages())
                .totalElements(branchPage.getTotalElements())
                .build();
    }

    
    //모든 지점 목록을 조회합니다.
    public List<BranchesDTO> listAllBranches() {
        List<BranchEntity> branches = branchesRepository.findAll();
        return branches.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }


     //지점의 활성 상태를 토글합니다.
    @Transactional
    public void toggleBranchStatus(Integer id, BranchesDTO branchesDTO) {
    	System.out.println("toggleBranchStatus 진입");
    	Optional<BranchEntity> op = branchesRepository.findById(id);
    	if(op.isPresent()) {
    		BranchEntity be = op.get();
    		be.setActive(!be.isActive());
    		branchesRepository.save(be);
    	}
    }

    
    //지점을 삭제합니다.
    @Transactional
    public void deleteBranch(Integer id) {
        if (!branchesRepository.existsById(id)) {
            throw new EntityNotFoundException("Branch not found with id: " + id);
        }
        branchesRepository.deleteById(id);
    }

    //DTO를 Entity로 변환합니다.
    private BranchEntity convertToEntity(BranchesDTO branchesDTO) {
        return BranchEntity.builder()
            .branchId(branchesDTO.getBranchId())
            .name(branchesDTO.getBranchesName())
            .address(branchesDTO.getAddress())
            .phone(branchesDTO.getPhone())
            .active(branchesDTO.isActive())
            .build();
    }

    //Entity를 DTO로 변환합니다.
    private BranchesDTO convertToDTO(BranchEntity branchEntity) {
        return BranchesDTO.builder()
            .branchId(branchEntity.getBranchId())
            .branchesName(branchEntity.getName())
            .address(branchEntity.getAddress())
            .phone(branchEntity.getPhone())
            .active(branchEntity.isActive())
            .build();
    }
    
//  // BranchEntity를 BranchesDTO로 변환하는 메서드
//  private BranchesDTO convertToDTO(BranchEntity branchEntity) {
//      return new BranchesDTO(branchEntity.getBranchId(),
//                             branchEntity.getName(),
//                             branchEntity.getAddress(),
//                             branchEntity.getPhone(),
//                             branchEntity.isActive());
//  }

    @Transactional(readOnly = true)
    public BranchesDTO getBranchById(Integer id) {
    	BranchEntity be = branchesRepository.findById(id).get();
    	BranchesDTO dto = BranchesDTO.builder()
    			.branchId(be.getBranchId())
    			.branchesName(be.getName())
    			.address(be.getAddress())
    			.phone(be.getPhone())
    			.active(be.isActive())
    			.build();
        return dto;
    }
    
    
}