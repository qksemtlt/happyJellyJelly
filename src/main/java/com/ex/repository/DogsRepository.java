package com.ex.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ex.entity.DogsEntity;
import com.ex.entity.MembersEntity;

public interface DogsRepository extends JpaRepository<DogsEntity, Integer>{
	 // 추가
	List<DogsEntity> findByMember(MembersEntity member);
//	Page<DogsEntity> findAll(Specification<DogsEntity> spec , Pageable pageable);
//	Page<DogsEntity> findAll(Pageable pageable);
}
