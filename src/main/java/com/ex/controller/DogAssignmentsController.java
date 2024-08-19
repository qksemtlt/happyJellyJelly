package com.ex.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ex.data.BranchesDTO;
import com.ex.data.DogAssignmentsDTO;
import com.ex.data.MonthcareGroupsDTO;
import com.ex.entity.MembersEntity;
import com.ex.service.BranchesService;
import com.ex.service.DogAssignmentsService;
import com.ex.service.MembersService;
import com.ex.service.MonthcareGroupsService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/dogassignments")
@RequiredArgsConstructor
public class DogAssignmentsController {

    private final DogAssignmentsService dogAssignmentsService;
    private final MonthcareGroupsService monthcareGroupsService;
    private final BranchesService branchesService; // 지점 정보를 가져오기 위해 추가
    private final MembersService membersService;
    @GetMapping("/dogassignmentsList")
    public String listAssignments(Principal principal, Model model) {
        MembersEntity member = membersService.findByUsername(principal.getName());
        Integer branchId = member.getBranchId();

        List<MonthcareGroupsDTO> groups = monthcareGroupsService.getMonthcareGroupByBranch(branchId);

        Map<Integer, List<DogAssignmentsDTO>> assignmentsByGroup = new HashMap<>();
        for (MonthcareGroupsDTO group : groups) {
            List<DogAssignmentsDTO> assignments = dogAssignmentsService.getAssignmentsByGroup(group.getId());
            assignmentsByGroup.put(group.getId(), assignments);
        }

        model.addAttribute("groups", groups);
        model.addAttribute("assignmentsByGroup", assignmentsByGroup);
        return "dogassignments/dogassignmentsList";
    }

 

    // 기타 필요한 메서드들...
}