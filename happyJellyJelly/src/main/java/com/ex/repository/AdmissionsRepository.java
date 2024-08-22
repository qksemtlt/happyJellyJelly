package com.ex.repository;
import com.ex.entity.AdmissionsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AdmissionsRepository extends JpaRepository<AdmissionsEntity, Integer> {
    List<AdmissionsEntity> findByAdmissionId(Integer admissionId);;
    List<AdmissionsEntity> findByStatus(String status);
    Page<AdmissionsEntity> findByDogs_Member_Username(String username, Pageable pageable);  
    Page<AdmissionsEntity>findAll(Pageable pageable);
    public int countByStatusAndDogs_DogId(String status, Integer dog_id);
    Page<AdmissionsEntity> findByBranch_BranchId(Integer branchId, Pageable pageable);
}