package com.ex.service;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;

import com.ex.entity.DogsEntity;
import com.ex.entity.MembersEntity;
import com.ex.repository.MembersRepository;
import com.ex.repository.dogRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DogService {

	@Autowired
	private final dogRepository dogRepository;
	@Autowired
	private final MembersRepository membersRepository;
	
	public List<DogsEntity> dogsAll(){
		return dogRepository.findAll();
	}
	
//	public Optional<MembersEntity> getMemberName(Integer id) {
		
//		return membersRepository.findAllById(Iterable<ID> ids)
//	}
	
//	public List<DogsEntity> selectDog(){
//		return dogRepository.findById(null)
//	}
	
}
