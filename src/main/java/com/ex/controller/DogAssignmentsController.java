package com.ex.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;



import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/dogassignments")
@RequiredArgsConstructor
public class DogAssignmentsController {
	
	@GetMapping("/dogassignmentsList")
	public String dogassignmentsList() {
		
	return "dogassignments/dogassignmentsList";
	}
}
