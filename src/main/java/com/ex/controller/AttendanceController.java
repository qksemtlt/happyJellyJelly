package com.ex.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ex.data.AttendanceDTO;
import com.ex.entity.MembersEntity;
import com.ex.repository.TestMapper;
import com.ex.service.AttendanceService;
import com.ex.service.MembersService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequestMapping("/attendance*")
@RequiredArgsConstructor
public class AttendanceController {

	@Autowired
	private AttendanceService attendanceService;
	@Autowired
	private MembersService membersService;
	
	private final TestMapper testMapper;
	
	@GetMapping("test")
	@ResponseBody
	public int test() {
		return 0;
	}

	
	@GetMapping("")
    public String getAttendance(@RequestParam(value = "date", required = false) String date,
                                Model model, Principal principal) {
		
		MembersEntity me = membersService.findByUsername(principal.getName());
		
        LocalDate currentDate = (date != null) ? LocalDate.parse(date) : LocalDate.now();
//        List<AttendanceDTO> attendances = attendanceService.getAttendanceDate(currentDate);
        List<AttendanceDTO> attendances = attendanceService.dateAndBranchAttendence(currentDate, me.getBranchId());

        System.out.println("attendances ::: " + attendances.toString());
        model.addAttribute("currentDate", currentDate);
        model.addAttribute("attendances", attendances);

        return "attendance/attendanceList";
    }
	
}
