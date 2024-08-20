package com.ex.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ex.entity.DogsEntity;
import com.ex.entity.MembersEntity;

<<<<<<< HEAD
public interface DogsRepository extends JpaRepository<DogsEntity, Integer> {
=======
public interface DogsRepository extends JpaRepository<DogsEntity, Integer>{
	
>>>>>>> branch 'woocheol' of https://github.com/gomting0/happyJelly.git
	List<DogsEntity> findByMember(MembersEntity member);
<<<<<<< HEAD

	@Query("SELECT d FROM DogsEntity d " 
			+ "JOIN d.dogAssign da " 
			+ "JOIN da.monthgroup mg " 
			+ "JOIN mg.branches b "
			+ "WHERE b.branchId = :branchId")
	List<DogsEntity> findByBranch(@Param("branchId") Integer branchId);
}
=======
	
	@Query("SELECT d FROM DogsEntity d " +
	           "JOIN d.dogAssign da " +
	           "JOIN da.monthgroup mg " +
	           "JOIN mg.branches b " +
	           "WHERE b.branchId = :branchId")
    List<DogsEntity> findByBranch(@Param("branchId")Integer branchId);
	
}
>>>>>>> branch 'woocheol' of https://github.com/gomting0/happyJelly.git
