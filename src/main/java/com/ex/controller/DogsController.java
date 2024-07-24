package com.ex.controller;

import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ex.data.DogsDTO;
import com.ex.entity.DogsEntity;
import com.ex.repository.dogsRepository;
import com.ex.service.DogService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/dogs/*")
@RequiredArgsConstructor
public class DogsController {
	
	@Autowired
	private DogService dogService;
	
//	 (관리자) 강아지 전체 목록 출력
	@GetMapping("list")
	public String dogList(Model model) {
		model.addAttribute("dogsList", dogService.dogsAll());
		return "dogs/dogList";
	}
	
	@GetMapping("search")
	public String search(DogsDTO dogsDTO) {
		return "dogs/dogList";
	};
	
	@GetMapping("detail/{dog_id}")
	public String dogDetail(Model model, @PathVariable("dog_id") Integer id,
			DogsDTO dogsDTO) {
		
		Optional<DogsEntity> dogEntityOptional = dogService.selectDog(id);
	    if (dogEntityOptional.isPresent()) {
	        model.addAttribute("dogdetail", dogEntityOptional.get());
	    } else {
	        model.addAttribute("dogdetail", null);
	    }
		
		return "dogs/dogDetail";
	};
	
	@GetMapping("update")
	@PreAuthorize("isAuthenticated()")
	public String dogupdate(Principal principal, Model model,
			DogsDTO dogsDTO) {
		model.addAttribute("dogsDTO", dogsDTO);
		return "dogs/dogDetail";
	};
}
