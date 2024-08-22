package com.ex.controller;
import java.security.Principal;
import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.ex.data.SubscriptionsDTO;
import com.ex.service.SubscriptionsService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/subs/*")
public class SubscriptionsController {
	
	private final SubscriptionsService subscriptionsService;
	
	@GetMapping("list")
	@PreAuthorize("isAuthenticated()")
	public String mySubsInfo(Principal principal, Model model) {
		List<SubscriptionsDTO> subsDTO = subscriptionsService.mysubsInfo(principal.getName());
		model.addAttribute("subsDTO", subsDTO);
		return "subscriptions/mysubs_info";
	}
}
