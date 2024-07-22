package com.ex.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ex.entity.AdmissionsEntity;

@Repository
public interface AdmissionsRepository  extends JpaRepository<AdmissionsEntity, Long> {

  
   
}
