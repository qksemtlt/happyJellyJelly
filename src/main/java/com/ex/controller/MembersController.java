package com.ex.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.ex.data.MembersDTO;
import com.ex.service.MembersService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/members/*")
public class MembersController {
	private final MembersService MembersService;
	
	@GetMapping("login")
	public String login() {
		return "members/login";
	}
	
	@GetMapping("signup")
	public String signup() {
		return "members/signup";
	}
	
	@PostMapping("signupPro")
	public String signupPro(MembersDTO membersDTO) {
		System.out.println(membersDTO.getEmail());
		MembersService.createMember(membersDTO);
		System.out.println(membersDTO.getName());
		return "main";
	}
}
