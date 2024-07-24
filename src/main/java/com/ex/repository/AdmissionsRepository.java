package com.ex.repository;

import com.ex.entity.AdmissionsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdmissionsRepository extends JpaRepository<AdmissionsEntity, Integer> {
    List<AdmissionsEntity> findByAdmissionId(Integer admissionId);;
    List<AdmissionsEntity> findByStatus(String status);
}