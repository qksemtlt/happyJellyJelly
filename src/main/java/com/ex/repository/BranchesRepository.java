package com.ex.repository;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.ex.entity.BranchEntity;

@Repository
public interface BranchesRepository extends JpaRepository<BranchEntity, Integer> {
    
    // 활성 상태 내림차순, 그 다음 이름 오름차순으로 정렬 (페이징 지원)
    @Query("SELECT b FROM BranchEntity b ORDER BY b.active DESC, b.name ASC")
    Page<BranchEntity> findAllOrderByActiveDescNameAsc(Pageable pageable);
    
    // 활성화된 지점만 검색 (페이징 지원)
    Page<BranchEntity> findByActiveTrue(Pageable pageable);
    
    // 통합 검색 메서드 (페이징 및 정렬 지원)
    @Query("SELECT b FROM BranchEntity b WHERE " +
              "(:activeOnly = false OR b.active = true) AND " +
              "(LOWER(b.name) LIKE LOWER(CONCAT('%', :term, '%')) OR " +
              "LOWER(b.address) LIKE LOWER(CONCAT('%', :term, '%')) OR " +
              "LOWER(b.address2) LIKE LOWER(CONCAT('%', :term, '%')) OR " +
              "LOWER(b.postCode) LIKE LOWER(CONCAT('%', :term, '%')) OR " +
              "LOWER(b.phone) LIKE LOWER(CONCAT('%', :term, '%')))")
   Page<BranchEntity> searchBranches(@Param("term") String term, 
                                     @Param("activeOnly") boolean activeOnly, 
                                     Pageable pageable);
    
    // Monthcare 그룹과 함께 모든 지점 조회
    @Query("SELECT DISTINCT b FROM BranchEntity b LEFT JOIN FETCH b.month")
    List<BranchEntity> findAllWithMonthcareGroups();
    
    // Monthcare 그룹과 함께 모든 지점 조회 (페이징 지원)
    @Query("SELECT b FROM BranchEntity b LEFT JOIN FETCH b.month")
    Page<BranchEntity> findAllBranchesWithMonthcareGroups(Pageable pageable);
    
    // 활성화된 지점 목록 조회
    List<BranchEntity> findByActiveTrue();
}