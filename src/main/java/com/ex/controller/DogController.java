package com.ex.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ex.data.DogsDTO;
import com.ex.repository.dogRepository;
import com.ex.service.DogService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/dogs/*")
@RequiredArgsConstructor
public class DogController {
	
	@Autowired
	private DogService dogService;
	
	@GetMapping("list")
	public String dogList(Model model) {
		model.addAttribute("dogsList", dogService.dogsAll());
//		model.addAttribute("", DogService.getMemberName());
		return "dogs/dogList";
	}
	
	@GetMapping("search")
	public String search(DogsDTO dogsDTO) {
		return "dogs/dogList";
	};
	
	@GetMapping("detail")
	public String dogDetail(Model model) {
//		model.addAttribute("dog", dogService.)
		return "dogs/dogDetail";
	};
	
	@GetMapping("update")
	public String dogupdate(DogsDTO dogsDTO) {
		return "dogs/dogDetail";
	};
}
