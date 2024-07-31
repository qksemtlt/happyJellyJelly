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

import com.ex.data.DogsDTO;
import com.ex.entity.DogsEntity;
import com.ex.entity.MembersEntity;
import com.ex.repository.MembersRepository;
import com.ex.repository.DogsRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DogService {

	@Autowired
	private final DogsRepository dogRepository;
	@Autowired
	private final MembersRepository membersRepository;
	
	public List<DogsEntity> dogsAll(){
		return dogRepository.findAll();
	}
	
//	public Optional<MembersEntity> getMemberName(Integer id) {
		
//		return membersRepository.findAllById(Iterable<ID> ids)
//	}
	
	public Optional<DogsEntity> selectDog(Integer id){
		return dogRepository.findById(id);
	}
	
	public void addDogs(String username, DogsDTO dogsDTO) {
		Optional<MembersEntity> op = membersRepository.findByUsername(username);
		if(op.isPresent()) {
			MembersEntity me = op.get();
			DogsEntity de = DogsEntity.builder().dogname(dogsDTO.getDogname()).breed(dogsDTO.getBreed())
					.weight(dogsDTO.getWeight()).birth_date(dogsDTO.getBirth_date())
					.gender(dogsDTO.getGender()).dog_serialnum(dogsDTO.getDog_serialnum())				

					.member(me).build();
			dogRepository.save(de);
		}
	}
	//방금추가
	  public List<DogsEntity> findDogsByMember(MembersEntity member) {
	        return dogRepository.findByMember(member);
	    }
	
}
