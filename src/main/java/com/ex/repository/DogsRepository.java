package com.ex.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ex.entity.DogsEntity;
import com.ex.entity.MembersEntity;

public interface DogsRepository extends JpaRepository<DogsEntity, Integer> {
	List<DogsEntity> findByMember(MembersEntity member);

	@Query("SELECT d FROM DogsEntity d " 
			+ "JOIN d.dogAssign da " 
			+ "JOIN da.monthgroup mg " 
			+ "JOIN mg.branches b "
			+ "WHERE b.branchId = :branchId")
	List<DogsEntity> findByBranch(@Param("branchId") Integer branchId);
}
