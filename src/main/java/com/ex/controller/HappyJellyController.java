package com.ex.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/diary/*")
@RequiredArgsConstructor
public class HappyJellyController {

	@GetMapping("/")
	public String main() {
//		public String main(DiaryDTO diaryDTO, Principal principal, Model model) {
//		List<DiaryDTO> list = userService.getDiary(principal.getName());
//		model.addAttribute("list", list);
//		System.out.println("list.size() ::: " + list.size());
//		diaryService.DiaryAll(principal.getName());
		return "calendar";
	}

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
}
