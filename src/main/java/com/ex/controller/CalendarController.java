package com.ex.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ex.data.CalendarDTO;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/calendar/*")
@RequiredArgsConstructor
public class CalendarController {

	@GetMapping("")
//	public String main() {
	public String main(CalendarDTO diaryDTO, Principal principal, Model model) {
		List<CalendarDTO> list=null;
//		List<DiaryDTO> list = userService.getDiary(principal.getName());
		model.addAttribute("list", list);
//		System.out.println("list.size() ::: " + list.size());
//		diaryService.DiaryAll(principal.getName());
		return "calendar/calendar";
	}
/*
	// 알림장상세조회
	@GetMapping("select")
	public String select(@RequestParam("selectDate") String selectDate
			, @RequestParam("diaryId") Long diaryId, Model model) {
		System.out.println("diaryId ::: " + diaryId);

		model.addAttribute("diaryId", diaryId);
		model.addAttribute("selectDate", selectDate);
//			DiaryDTO diaryDTO = diaryService.getDiaryById(diaryId);
//			model.addAttribute("diaryDTO", diaryDTO);

		return "viewDiary";
	}
	
	// 일지등록
	@PostMapping("create")
	public String create(DiaryDTO diaryDTO, Principal principal, @RequestParam("selectDate") String selectDate) {
	
		System.out.println("일지등록 다이어리 컨트롤러 selectDate ::: " + selectDate);
		
//		this.diaryService.create(diaryDTO, principal.getName(), selectDate);
	
		return "redirect:/";
	}
*/
}
