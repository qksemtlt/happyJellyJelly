package com.ex.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ex.data.DogsDTO;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/dogs/*")
@RequiredArgsConstructor
public class DogController {
	
	@GetMapping("")
	public String dogList(DogsDTO dogsDTO) {
		return "dogs/dogList";
	}
}
