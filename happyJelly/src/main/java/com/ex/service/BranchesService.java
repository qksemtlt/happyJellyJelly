package com.ex.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
   private static final Logger logger = LoggerFactory.getLogger(BranchesService.class);
   
   private final BranchesRepository branchesRepository;
    private final KakaoApiService kakaoApiService; 
 
    @Transactional
    public BranchesDTO registerBranch(BranchesDTO branchesDTO) {
        // 좌표 변환 로직
        Map<String, Double> coordinates = kakaoApiService.getCoordinatesFromAddress(
            branchesDTO.getPostCode(), 
            branchesDTO.getAddress(), 
            branchesDTO.getAddress2()
        );
        
        branchesDTO.setLatitude(coordinates.get("latitude"));
        branchesDTO.setLongitude(coordinates.get("longitude"));
        branchesDTO.setActive(true);
         
        BranchEntity branch = convertToEntity(branchesDTO);
        BranchEntity savedBranch = branchesRepository.save(branch);
        
        return convertToDTO(savedBranch);
    }

    public BranchesListResponseDTO listBranchesSortedByActiveAndName(int page, int size) {
        Sort sort = Sort.by(Sort.Order.desc("active"), Sort.Order.asc("name"));
        PageRequest pageRequest = PageRequest.of(page, size, sort);

        Page<BranchEntity> branchPage = branchesRepository.findAll(pageRequest);

        List<BranchesDTO> branchesDTOs = branchPage.getContent().stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());

        return BranchesListResponseDTO.builder()
                .branches(branchesDTOs)
                .totalPages(branchPage.getTotalPages())
                .totalElements(branchPage.getTotalElements())
                .currentPage(branchPage.getNumber())
                .build();
    }
    
    public List<BranchesDTO> getAllActiveBranches() {
        return branchesRepository.findByActiveTrue().stream()
                                  .map(this::convertToDTO)
                                  .collect(Collectors.toList());
    }

    // 메서드 시그니처를 수정하여 activeOnly 파라미터 추가
    public BranchesListResponseDTO searchBranches(String term, int page, int size, String sortBy, String sortDir, boolean activeOnly) {
        Sort sort = Sort.by(sortDir.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy);
        PageRequest pageRequest = PageRequest.of(page, size, sort);

        Page<BranchEntity> branchPage = branchesRepository.searchBranches(term, activeOnly, pageRequest);

        List<BranchesDTO> branchesDTOs = branchPage.getContent().stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());

        return BranchesListResponseDTO.builder()
            .branches(branchesDTOs)
            .totalPages(branchPage.getTotalPages())
            .totalElements(branchPage.getTotalElements())
            .currentPage(branchPage.getNumber())
            .build();
    }

    @Transactional
    public BranchEntity updateBranch(Integer id, BranchesDTO branchesDTO) {
        BranchEntity branch = branchesRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Branch not found"));

        branch.setName(branchesDTO.getBranchesName());
        branch.setPostCode(branchesDTO.getPostCode());
        branch.setAddress(branchesDTO.getAddress());
        branch.setAddress2(branchesDTO.getAddress2());
        branch.setPhone(branchesDTO.getPhone());
        branch.setActive(branchesDTO.getActive());  // 명시적으로 active 상태 설정
        
        // latitude와 longitude 설정 (BigDecimal로 변환 필요)
        if (branchesDTO.getLatitude() != null) {
            branch.setLatitude(BigDecimal.valueOf(branchesDTO.getLatitude()));
        }
        if (branchesDTO.getLongitude() != null) {
            branch.setLongitude(BigDecimal.valueOf(branchesDTO.getLongitude()));
        }

        return branchesRepository.save(branch);
    }

    public BranchesListResponseDTO listBranches(int page, int size, String sortBy, String sortDir, boolean activeOnly) {
        logger.info("Listing branches: page={}, size={}, sortBy={}, sortDir={}, activeOnly={}", page, size, sortBy, sortDir, activeOnly);
        
        Sort sort = Sort.by(sortDir.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy);
        PageRequest pageRequest = PageRequest.of(page, size, sort);

        Page<BranchEntity> branchPage;
        if (activeOnly) {
            branchPage = branchesRepository.findByActiveTrue(pageRequest);
        } else {
            branchPage = branchesRepository.findAllBranchesWithMonthcareGroups(pageRequest);
        }

        List<BranchesDTO> branchesDTOs = branchPage.getContent().stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());

        logger.info("Found {} branches", branchesDTOs.size());

        return BranchesListResponseDTO.builder()
                .branches(branchesDTOs)
                .totalPages(branchPage.getTotalPages())
                .totalElements(branchPage.getTotalElements())
                .currentPage(branchPage.getNumber())
                .build();
    }

    @Transactional
    public void toggleBranchStatus(Integer id) {
        BranchEntity branch = branchesRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Branch not found with id: " + id));
        branch.setActive(!branch.getActive());
        branchesRepository.save(branch);
    }

    @Transactional
    public void deleteBranch(Integer id) {
        if (!branchesRepository.existsById(id)) {
            throw new EntityNotFoundException("Branch not found with id: " + id);
        }
        branchesRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public BranchesDTO getBranchById(Integer id) {
        return branchesRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new EntityNotFoundException("Branch not found with id: " + id));
    }

    private BranchEntity convertToEntity(BranchesDTO dto) {
        return BranchEntity.builder()
                .branchId(dto.getBranchId())
                .name(dto.getBranchesName())
                .postCode(dto.getPostCode())
                .address(dto.getAddress())
                .address2(dto.getAddress2())
                .phone(dto.getPhone())
                .active(dto.getActive())
                .latitude(dto.getLatitude() != null ? 
                        BigDecimal.valueOf(dto.getLatitude()).setScale(8, RoundingMode.HALF_UP) : null)
                .longitude(dto.getLongitude() != null ? 
                        BigDecimal.valueOf(dto.getLongitude()).setScale(8, RoundingMode.HALF_UP) : null)
                .build();
    }

    private BranchesDTO convertToDTO(BranchEntity entity) {
        return BranchesDTO.builder()
                .branchId(entity.getBranchId())
                .branchesName(entity.getName())
                .postCode(entity.getPostCode())
                .address(entity.getAddress())
                .address2(entity.getAddress2())
                .phone(entity.getPhone())
                .active(entity.getActive())
                .latitude(entity.getLatitude() != null ? entity.getLatitude().doubleValue() : null)
                .longitude(entity.getLongitude() != null ? entity.getLongitude().doubleValue() : null)
                .build();
    }

    private void updateBranchFields(BranchEntity branch, BranchesDTO dto, Map<String, Double> coordinates) {
        branch.setName(dto.getBranchesName());
        branch.setPostCode(dto.getPostCode());
        branch.setAddress(dto.getAddress());
        branch.setAddress2(dto.getAddress2());
        branch.setPhone(dto.getPhone());
        branch.setLatitude(BigDecimal.valueOf(coordinates.get("latitude")));
        branch.setLongitude(BigDecimal.valueOf(coordinates.get("longitude")));
    }
}