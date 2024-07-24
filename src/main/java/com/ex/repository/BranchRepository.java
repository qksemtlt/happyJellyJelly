package com.ex.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ex.entity.BranchEntity;

@Repository
public interface BranchRepository extends JpaRepository<BranchEntity, Integer> {
	
}
