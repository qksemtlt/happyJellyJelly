package com.ex.controller;
import java.security.Principal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.ex.data.DailyReportsDTO;
import com.ex.service.DailyReportsService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/calendar/*")
@RequiredArgsConstructor
public class DailyReportsController {
	
	@Autowired
	private DailyReportsService dailyReportsService;
	
	
	// 캘린더에 알림장 표시
	@GetMapping("")
	public String main(DailyReportsDTO daReportsDTO, Principal principal, Model model) {
		List<DailyReportsDTO> list = dailyReportsService.getDailyReportsList(principal.getName());
		model.addAttribute("list", list);
		return "calendar/calendar";
	}
	
	
	// 알림장 상세조회
	@GetMapping("select")
	public String select(@RequestParam("selectDate") String selectDate,
			@RequestParam("reportId") Integer reportId, Model model) {
		System.out.println("reportId ::: " + reportId);
		model.addAttribute("reportId", reportId);
		model.addAttribute("selectDate", selectDate);
		DailyReportsDTO daReportsDTO = dailyReportsService.getDailyReports(reportId);
		model.addAttribute("daReportsDTO", daReportsDTO);
		return "calendar/viewDailyReports";
	}
	
	
	// 알림장 작성폼
	@GetMapping("create")
	public String create(@RequestParam("selectDate") String selectDate,
			Model model) {
		System.out.println("일지등록 다이어리 컨트롤러 selectDate ::: " + selectDate);
		model.addAttribute("selectDate", selectDate);
//		model.addAttribute("title", selectDate);
//		model.addAttribute("contents", selectDate);
//		model.addAttribute("_csrf.parameterName", selectDate);
		return "calendar/createDailyReports";
	}
	
	
	// 알림장 등록
	@PostMapping("create")
	public String create(DailyReportsDTO dailyReportsDTO
							, Principal principal
							, @RequestParam("attId") Integer attendanceId
							, @RequestParam("selectDate") String selectDate) {
		this.dailyReportsService.create(dailyReportsDTO, attendanceId, principal.getName(), selectDate);
		return "redirect:calendar";
	}
	

}
