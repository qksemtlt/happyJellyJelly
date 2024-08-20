package com.ex.controller;

import java.security.Principal;
import java.util.Map;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.ex.service.DogAssignmentsService;
import com.ex.service.MembersService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/dogassignments")
@RequiredArgsConstructor
public class DogAssignmentsController {
    
    private final DogAssignmentsService dogAssignmentsService;
    private final MembersService membersService;

    @GetMapping("/dogassignmentsList")
    @PreAuthorize("isAuthenticated()")
    public String listAssignments(Principal principal, Model model) {
        // 현재 로그인한 사용자의 지점 ID를 가져옵니다.
        Integer branchId = membersService.findByUsername(principal.getName()).getBranchId();

        // 서비스에서 모든 필요한 정보를 한 번에 가져옵니다.
        Map<String, Object> assignmentsInfo = dogAssignmentsService.getAssignmentsInfoByBranch(branchId);

        // 모델에 정보를 추가합니다.
        model.addAttribute("groups", assignmentsInfo.get("groups"));
        model.addAttribute("assignmentsByGroup", assignmentsInfo.get("assignmentsByGroup"));
        model.addAttribute("studentCountByGroup", assignmentsInfo.get("studentCountByGroup"));

        return "dogassignments/dogassignmentsList";
    }

    // 필요에 따라 추가적인 메서드를 구현할 수 있습니다.
    // 예: 새로운 강아지 배정 생성, 배정 수정, 배정 삭제 등
}