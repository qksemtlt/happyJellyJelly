package com.ex.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ex.entity.MonthcareGroupsEntity;

@Repository
public interface MonthcareGroupsRepository extends JpaRepository<MonthcareGroupsEntity, Integer>{

	public List<MonthcareGroupsEntity> findByBranchesAndTeachers(Integer branchId, Integer teacherId);
}
