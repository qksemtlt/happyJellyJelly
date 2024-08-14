package com.ex.controller;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.ex.service.SubscriptionsService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/subs/*")
public class SubscriptionsController {
	
	private final SubscriptionsService subscriptionsService;
	
	@GetMapping("list")
	@PreAuthorize("isAuthenticated()")
	public String mySubsInfo() {
		return "subscriptions/mysubs_info";
	}

}
