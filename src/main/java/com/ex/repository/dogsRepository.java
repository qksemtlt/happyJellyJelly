package com.ex.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ex.entity.DogsEntity;

public interface dogsRepository extends JpaRepository<DogsEntity, Integer>{

//	Page<DogsEntity> findAll(Specification<DogsEntity> spec , Pageable pageable);
//	Page<DogsEntity> findAll(Pageable pageable);
}
