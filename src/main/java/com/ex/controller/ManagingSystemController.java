package com.ex.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.ex.data.BranchesDTO;
import com.ex.data.StaffMgDTO;
import com.ex.service.BranchesService;
import com.ex.service.StaffMgService;
import lombok.RequiredArgsConstructor;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/managingSys/*")
public class ManagingSystemController {

    private final StaffMgService staffMgService;
    private final BranchesService branchesService;

    // 기본 페이지로 리다이렉트
    @GetMapping("")
    public String redirectToDefaultPage() {
        return "redirect:/managingSys/branches";
    }

    // 지점 목록 페이지
    @GetMapping("branches")
    public String listBranches(Model model) {
        List<BranchesDTO> branches = branchesService.getAllBranches();
        model.addAttribute("branchList", branches);
        return "managingSys/branches/branchesManagement";
    }

    // 직원 관리 페이지
    @GetMapping("staff")
    public String showStaffManagement(Model model) {
        List<StaffMgDTO> staffDetail = staffMgService.getAllStaff();
        model.addAttribute("staffList", staffDetail);
        return "managingSys/staffDetail/staffManagement";
    }

    // 회원 관리 페이지
    @GetMapping("member")
    public String showMemberManagement() {
        return "managingSys/member/memberManagement";
    }
}