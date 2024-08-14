package com.ex.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.ex.entity.DogAssignmentsEntity;


@Repository
public interface DogAssignmentsRepository extends JpaRepository<DogAssignmentsEntity, Integer>{

}
