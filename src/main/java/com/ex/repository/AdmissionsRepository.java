package com.ex.repository;

import com.ex.entity.AdmissionsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdmissionsRepository extends JpaRepository<AdmissionsEntity, Long> {
    List<AdmissionsEntity> findByDogId(Long dogId);
    List<AdmissionsEntity> findByStatus(String status);
}