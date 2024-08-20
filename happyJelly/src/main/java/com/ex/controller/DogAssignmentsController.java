package com.ex.controller;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.ex.data.DogAssignmentsDTO;
import com.ex.data.MonthcareGroupsDTO;
import com.ex.entity.MembersEntity;
import com.ex.service.BranchesService;
import com.ex.service.DogAssignmentsService;
import com.ex.service.MembersService;
import com.ex.service.MonthcareGroupsService;
import lombok.RequiredArgsConstructor;

@Controller // 이 클래스가 Spring MVC의 컨트롤러임을 나타냅니다.
@RequestMapping("/dogassignments") // 이 컨트롤러의 기본 URL 경로를 지정합니다.
@RequiredArgsConstructor // Lombok 어노테이션으로, final 필드에 대한 생성자를 자동으로 생성합니다.
public class DogAssignmentsController {
    // 필요한 서비스들을 주입받습니다.
    private final DogAssignmentsService dogAssignmentsService;
    private final MonthcareGroupsService monthcareGroupsService;
    private final BranchesService branchesService; // 지점 정보를 가져오기 위해 추가
    private final MembersService membersService;

    @GetMapping("/dogassignmentsList") // GET 요청으로 /dogassignments/dogassignmentsList 경로에 매핑됩니다.
    @PreAuthorize("isAuthenticated()")
    public String listAssignments(Principal principal, Model model) {
        // 현재 로그인한 사용자의 정보를 가져옵니다.
        MembersEntity member = membersService.findByUsername(principal.getName());
        Integer branchId = member.getBranchId();

        // 해당 지점의 모든 그룹을 가져옵니다.
        List<MonthcareGroupsDTO> groups = monthcareGroupsService.getMonthcareGroupByBranch(branchId);

        // 각 그룹별 강아지 배정 정보를 저장할 Map을 생성합니다.
        Map<Integer, List<DogAssignmentsDTO>> assignmentsByGroup = new HashMap<>();

        // 각 그룹에 대해 강아지 배정 정보를 조회하고 Map에 저장합니다.
        for (MonthcareGroupsDTO group : groups) {
            List<DogAssignmentsDTO> assignments = dogAssignmentsService.getAssignmentsByGroup(group.getId());
            assignmentsByGroup.put(group.getId(), assignments);
        }

        // 조회한 정보를 모델에 추가합니다.
        model.addAttribute("groups", groups);
        model.addAttribute("assignmentsByGroup", assignmentsByGroup);

        // 뷰 이름을 반환합니다.
        return "dogassignments/dogassignmentsList";
    }
 
    // 기타 필요한 메서드들...
}