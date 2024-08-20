package com.ex.controller;
import com.ex.data.BranchesDTO;
import com.ex.service.BranchesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/managingSys/branches/*")
public class BranchesController {
    private final BranchesService branchesService;

    // 지점 등록 폼 페이지
    @GetMapping("register")
    public String showRegisterForm(Model model) {
        model.addAttribute("branches", new BranchesDTO());
        return "managingSys/branches/branchesRegister";
    }

    // 지점 등록 처리
    @PostMapping("register")
    public String registerBranches(@ModelAttribute BranchesDTO branchesDTO) {
        branchesService.registerBranch(branchesDTO);
        return "redirect:/managingSys/branches";
    }

    // 지점 수정 폼 페이지
    @GetMapping("edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model) {
        BranchesDTO branches = branchesService.getBranchById(id);
        model.addAttribute("branches", branches);
        return "managingSys/branches/branchesEdit";
    }

    // 지점 정보 업데이트 처리
    @PostMapping("update")
    public String updateBranches(@ModelAttribute BranchesDTO branchesDTO) {
    	branchesService.updateBranch(branchesDTO.getBranchId(), branchesDTO);
        return "redirect:/managingSys/branches";
    }

    // 지점 상태 토글 (활성/비활성)
    @PostMapping("toggle-status/{id}")
    public String toggleBranchStatus(@PathVariable("id") Integer id, 
    											@ModelAttribute BranchesDTO branchesDTO) {
            branchesService.toggleBranchStatus(id, branchesDTO);
            return "redirect:/managingSys/branches";
    }
    
    // 지점 삭제 처리 (AJAX 요청 처리)
    @PostMapping("delete/{id}")
    public ResponseEntity<?> deleteBranches(@PathVariable("id") Integer id) {
        try {
            branchesService.deleteBranch(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error deleting branch: " + e.getMessage());
        }
    }
}