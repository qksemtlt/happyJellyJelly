package com.ex.controller;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.ex.data.BranchesListResponseDTO;
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

//    @GetMapping("branches")
//    public String listBranches(@RequestParam(defaultValue = "0") int page,
//          @RequestParam(defaultValue = "10") int size,
//          Model model) {
//       BranchesListResponseDTO response = branchesService.listBranchesSortedByActiveAndName(page, size);
//       model.addAttribute("branchesResponse", response);
//       return "managingSys/branches/branchesManagement";
//    }
    
    // 지점 목록 페이지
    @GetMapping("branches")
    public String listBranches(Model model,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sortBy", defaultValue = "active") String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "desc") String sortDir,
            @RequestParam(value = "activeOnly", defaultValue = "false") boolean activeOnly) {
         BranchesListResponseDTO branchesResponse;
         if ("active".equals(sortBy) && "desc".equals(sortDir)) {
         branchesResponse = branchesService.listBranchesSortedByActiveAndName(page, size);
         } else {
         branchesResponse = branchesService.listBranches(page, size, sortBy, sortDir, activeOnly);
         }
         model.addAttribute("branchesResponse", branchesResponse);
         return "managingSys/branches/branchesManagement";
         }
   
    // 직원 관리 페이지
    @GetMapping("staff")
    public String showStaffManagement(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            Model model) {
        Page<StaffMgDTO> staffPage = staffMgService.getAllStaff(page, size);
        model.addAttribute("staffPage", staffPage);
        return "managingSys/staff/staffManagement";
    }
    // 회원 관리 페이지
    @GetMapping("member")
    public String showMemberManagement() {
        return "managingSys/member/memberManagement";
    }
}