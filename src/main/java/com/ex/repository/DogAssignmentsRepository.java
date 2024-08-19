package com.ex.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.ex.entity.DogAssignmentsEntity;
import com.ex.entity.DogsEntity;
import com.ex.entity.MonthcareGroupsEntity;


@Repository
public interface DogAssignmentsRepository extends JpaRepository<DogAssignmentsEntity, Integer> {
	  List<DogAssignmentsEntity> findByMonthgroup(MonthcareGroupsEntity monthgroup);
	Optional<DogAssignmentsEntity> findByDogsAndMonthgroup(DogsEntity dogs, MonthcareGroupsEntity monthgroup);
}




