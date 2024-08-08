package com.ex.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ex.data.AttendanceDTO;
import com.ex.service.AttendanceService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequestMapping("/attendance*")
@RequiredArgsConstructor
public class AttendanceController {

	@Autowired
	private AttendanceService attendanceService;
	
	@GetMapping("")
	public String getMethodName(AttendanceDTO attendanceDTO
								, Principal principal, Model model) {
		// 현재 날짜
		LocalDate currentDate = LocalDate.now();
        model.addAttribute("currentDate", currentDate);
        
        List<AttendanceDTO> attendances = attendanceService.getAttendanceDate(currentDate);
		model.addAttribute("attendances", attendances);
		return "attendance/attendanceList";
	}
	
}
