package com.ex.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ex.data.CalendarDTO;
import com.ex.data.DailyReportsDTO;
import com.ex.entity.DailyReportsEntity;
import com.ex.service.DailyReportsService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/calendar/*")
@RequiredArgsConstructor
public class DailyReportsController {
	
	@Autowired
	private DailyReportsService dailyReportsService;
	
	@GetMapping("")
	public String main(DailyReportsDTO daReportsDTO, Principal principal, Model model) {
		List<DailyReportsEntity> list = dailyReportsService.dailyReportsAll();
//		List<DailyReportsDTO> list = dailyReportsService.getDiary(principal.getName());
		model.addAttribute("list", list);
		System.out.println("list.size() ::: " + list.size());
//		diaryService.DiaryAll(principal.getName());
		return "calendar/calendar";
	}
	
	
	@GetMapping("select")
	public String select(@RequestParam("selectDate") String selectDate,
			@RequestParam("reportId") Long reportId, Model model) {
		System.out.println("reportId ::: " + reportId);
		model.addAttribute("reportId", reportId);
		model.addAttribute("selectDate", selectDate);
//		DailyReportsDTO daReportsDTO = null;
		DailyReportsDTO daReportsDTO = dailyReportsService.getDailyReports(reportId);
//		model.addAttribute("daReportsDTO", daReportsDTO);
		return "calendar/viewDailyReports";
	}
	
	// 일지작성폼
	@GetMapping("create")
	public String create(@RequestParam("selectDate") String selectDate,
			Model model) {
		System.out.println("일지등록 다이어리 컨트롤러 selectDate ::: " + selectDate);
		model.addAttribute("selectDate", selectDate);
		model.addAttribute("title", selectDate);
		model.addAttribute("contents", selectDate);
//		model.addAttribute("_csrf.parameterName", selectDate);
		return "calendar/createDailyReports";
	}
	
	// 일지등록
	@PostMapping("create")
	public String create(DailyReportsDTO dailyReportsDTO, Principal principal, @RequestParam("selectDate") String selectDate) {
//		this.dailyReportsService.create(dailyReportsDTO, principal.getName(), selectDate);
		return "redirect:calendar/";
	}
	

}
