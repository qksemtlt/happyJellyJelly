package com.ex.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;



import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admissions/*")
@RequiredArgsConstructor
public class AdmissionsController {
		
	 public String admissions() {
		
		return "admissions";
	}
	
	
}
