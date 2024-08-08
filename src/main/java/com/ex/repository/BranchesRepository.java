package com.ex.repository;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ex.entity.BranchEntity;

@Repository
public interface BranchesRepository extends JpaRepository<BranchEntity, Integer> {
    // 기본 제공 findAll() 메서드 사용

//    @Query("SELECT b FROM BranchEntity b WHERE b.branchId = :id")
    Optional<BranchEntity> findById(Integer id);
//    Optional<BranchEntity> findById(@Param("id") Integer id);

    @Query("SELECT b FROM BranchEntity b WHERE b.name LIKE %:keyword% OR b.address LIKE %:keyword%")
    List<BranchEntity> searchByKeyword(@Param("keyword") String keyword);

    List<BranchEntity> findByNameContainingIgnoreCaseOrAddressContainingIgnoreCase(String nameKeyword, String addressKeyword);
}
