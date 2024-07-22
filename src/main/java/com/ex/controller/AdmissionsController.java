package com.ex.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;



import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admissions/*")
@RequiredArgsConstructor
public class AdmissionsController {
	
	@GetMapping("")
	 public String admissions() {
		
		return "admissions/admissions";
	}
	
	@GetMapping("admissionsList")
	public String admissionsList() {
		
		return "admissions/admissionsList";
	}
	
	
}
