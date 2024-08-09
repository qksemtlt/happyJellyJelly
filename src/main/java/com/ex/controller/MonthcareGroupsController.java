package com.ex.controller;
import java.security.Principal;
import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.ex.data.MembersDTO;
import com.ex.data.MonthcareGroupsDTO;
import com.ex.service.MembersService;
import com.ex.service.MonthcareGroupsService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/month/*")
@RequiredArgsConstructor
public class MonthcareGroupsController {
	
	private final MonthcareGroupsService monthcareGroupService;
	private final MembersService membersService;
	
	@GetMapping("list")
	@PreAuthorize("isAuthenticated()")
	public String monthcareGroupList(Model model, Principal principal) {
		Integer branch_id = membersService.readMembersInfo(principal.getName()).getBranch_id();
		List<MonthcareGroupsDTO> monthcareList = monthcareGroupService.getMonthcareGroupByBranch(branch_id);
		model.addAttribute("monthcare", monthcareList);
		model.addAttribute("branch_id", branch_id);
		return "monthcaregroups/monthgroup_list";
	}
	
	@GetMapping("create")
	@PreAuthorize("isAuthenticated()")
	public String createMonthcareGroup(Model model, MonthcareGroupsDTO monthDTO, Principal principal) {
		Integer branch_id = membersService.readMembersInfo(principal.getName()).getBranch_id();
		model.addAttribute("branch_id", branch_id);
		model.addAttribute("monthDTO",monthDTO);
		List<MembersDTO> teacher = monthcareGroupService.getTeachers(branch_id);
		model.addAttribute("teachers", teacher);
		return "monthcaregroups/add_monthgroup";
	}
	
	@PostMapping("create")
	@PreAuthorize("isAuthenticated()")
	public String createMonthcareGroup(MonthcareGroupsDTO monthDTO, Principal principal) {
		Integer branch_id = membersService.readMembersInfo(principal.getName()).getBranch_id();
		monthcareGroupService.createMonthcareGroup(branch_id, monthDTO);
		return String.format("redirect:/month/list/%s", branch_id);
	}
	
	@GetMapping("delete/{id}")
	@PreAuthorize("isAuthenticated()")
	public String deleteMonthcareGroup(@PathVariable("id") Integer month_id) {
		return "";
	}
	
	@GetMapping("update/{id}")
	@PreAuthorize("isAuthenticated()")
	public String updateMonthcareGroup(@PathVariable("id") Integer month_id, Model model, Principal principal) {
		MonthcareGroupsDTO monthDTO = monthcareGroupService.getMonthGroup(month_id);
		model.addAttribute("monthDTO", monthDTO);
		Integer branch_id = membersService.readMembersInfo(principal.getName()).getBranch_id();
		List<MembersDTO> teacher = monthcareGroupService.getTeachers(branch_id);
		model.addAttribute("teachers", teacher);
		return "monthcaregroups/update_monthgroup";
	}
	
	@PostMapping("update")
	@PreAuthorize("isAuthenticated()")
	public String updateMonthcareGroups(MonthcareGroupsDTO monthDTO) {
		monthcareGroupService.updateMonthgroup(monthDTO.getId(), monthDTO);
		return "redirect:/month/list";
	}
}
