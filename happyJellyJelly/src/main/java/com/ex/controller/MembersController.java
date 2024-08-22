package com.ex.controller;
import java.security.Principal;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.ex.data.MembersDTO;
import com.ex.service.MembersService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/members/*")
public class MembersController {
	private final MembersService membersService;
	
	// 카카오, 네이버 로그인 시 가입 내역 유무 확인하는 메서드
	@GetMapping("socialCheck")
	@ResponseBody
	public void socialCheck(@RequestParam("username") String username, @RequestParam("name") String name) {
		membersService.socialCheck(username, name);		
	}

	
	@GetMapping("login")
	public String login() {
		return "members/login";
	}
	
	
	@GetMapping("signup")
	public String signup(MembersDTO membersDTO) {
		return "members/signup";
	}
	
	
	// 회원 가입 시 유효성 검사 진행 메세지
	@PostMapping("signup")
	public String signup(@Validated(MembersDTO.SignUp.class) MembersDTO membersDTO, BindingResult bindingResult) {
		if(!membersDTO.getPassword().equals(membersDTO.getPassword2())) {
			bindingResult.rejectValue("password2", "passwordMismatch","비밀번호가 일치하지 않습니다.");
			return "members/signup";
		}
		if(bindingResult.hasErrors()) {
			return "members/signup";
		}
		try {
			membersService.createMember(membersDTO);
		}catch(Exception e) {
			e.printStackTrace();
			bindingResult.reject("signupFailed", "이미 존재하는 ID입니다.");
			return "members/signup";
		}		
		return "redirect:/members/login";
	}
	
	
	@GetMapping("idLostForm")
	public String idLost() {
		return "members/id_lost";
	}
	
	
	@PostMapping("idLostPro")
	public String idLost(MembersDTO membersDTO, Model model) {
		String message = membersService.findMemberUsername(membersDTO);
		model.addAttribute("message", message);
		return "members/findUsername";
	}
	
	
	// 비 로그인 상태에서 비밀번호 변경 원할 때 form
	@GetMapping("pwLostForm")
	public String pwLost(MembersDTO membersDTO) {
		return "members/passwdForm";
	}
	
	
	// 비 로그인 상태에서 개인 정보 입력 후 일치하는 레코드가 있는지 확인하는 메서드
	// 일치하는 레코드가 1개 있으면 비밀번호 변경 form 으로 넘어감
	@PostMapping("pwLost")
	public String pwLost(MembersDTO membersDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		int count = membersService.passwdLostCheck(membersDTO);		
		if(count==1) {
			redirectAttributes.addAttribute("username", membersDTO.getUsername());
			return "redirect:/members/pwChange";
		}else {
			bindingResult.reject("infoNotFound", "일치하는 정보가 없습니다.");
			return "members/passwdForm";
		}
	}
	
	
	// 비밀번호 변경하는 form
	// 비 로그인 상태, 로그인 상태 구별하여 model 에 다른 값 보냄
	@GetMapping("pwChange")
	public String pwChange(@RequestParam(value = "username", required = false) String username, Principal principal, Model model) {
	    if (username != null) {
	        model.addAttribute("username", username);
	    } else if (principal != null) {
	        model.addAttribute("username", principal.getName());
	    } else {
	        model.addAttribute("errorMessage", "유효하지 않은 접근입니다. 로그인하거나 올바른 URL을 사용해주세요.");
	        model.addAttribute("username", "");
	    }
	    model.addAttribute("membersDTO", new MembersDTO());  
	    return "members/passwdChange";
	}
	
	
	// 비 로그인(principal==null), 로그인(principal!=null) 상태에 따라 다른 메서드 실행
	// 비밀번호 변경 유효성 검사 진행
	@PostMapping("pwChange")
	public String pwChange(@Validated(MembersDTO.PasswordChange.class) MembersDTO membersDTO, BindingResult bindingResult, Principal principal) {
		if(principal !=null) {
			if (!membersService.checkPassword(principal.getName(), membersDTO.getPassword())) {
	            bindingResult.rejectValue("password", "wrongPassword", "기존 비밀번호를 확인하세요.");
	            return "members/passwdChange";
	        }
			if(membersDTO.getPassword().equals(membersDTO.getNewpassword())) {
				bindingResult.rejectValue("newpassword", "samePassword","새 비밀번호가 기존 비밀번호와 동일합니다.");
				return "members/passwdChange";
			}
			if(!membersDTO.getNewpassword().equals(membersDTO.getPassword2())) {
				bindingResult.rejectValue("password2", "passwordIncorrect","비밀번호가 일치하지 않습니다.");
				return "members/passwdChange";
			}
			membersService.passwdChange(principal.getName(), membersDTO.getNewpassword());
		}else {
			if(!membersDTO.getPassword().equals(membersDTO.getPassword2())) {
				bindingResult.rejectValue("password2", "passwordIncorrect","비밀번호가 일치하지 않습니다.");
				return "members/passwdChange";
			}
			membersService.passwdChange(membersDTO.getUsername(), membersDTO.getPassword());
		}
		return "redirect:/members/login";
	}
	
	
	@GetMapping("mypage")
	@PreAuthorize("isAuthenticated()")
	public String mypage() {
		return "members/mypage";
	}
	
	
	@GetMapping("infoUpdate")
	@PreAuthorize("isAuthenticated()")
	public String infoUpdate(Principal principal, Model model) {
		MembersDTO membersDTO = membersService.readMembersInfo(principal.getName());
		model.addAttribute("membersDTO",membersDTO);
		return "members/info_update";
	}
	
	
	@PostMapping("infoUpdate")
	@PreAuthorize("isAuthenticated()")
	public String infoUpdate(MembersDTO membersDTO) {
		membersService.updateInfo(membersDTO);
		return "members/mypage";
	}
	
	
	@GetMapping("deleteAccount")
	@PreAuthorize("isAuthenticated()")
	public String deleteAccount(Principal principal, Model model, MembersDTO membersDTO) {
		model.addAttribute("username", principal.getName());
		return "members/delete_account";
	}
	
	
	@PostMapping("deleteAccount")
	@PreAuthorize("isAuthenticated()")
	public String deleteAccount(MembersDTO membersDTO, BindingResult bindingResult) {
		if (!membersService.checkPassword(membersDTO.getUsername(), membersDTO.getPassword())) {
            bindingResult.rejectValue("password", "wrongPassword", "기존 비밀번호를 확인하세요.");
            return "members/delete_account";
        }
		membersService.deleteMember(membersDTO.getUsername());		
		return "redirect:/members/logout";
	}
	
	
	// 네이버 로그인을 위해 callback.html 호출하는 메서드
	@GetMapping("callback")
	public String callback() {
		return "callback";
	}
}
