package com.ex.controller;

import com.ex.data.BranchesDTO;
import com.ex.service.BranchesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/managingSys/branches")
public class BranchesController {
    private final BranchesService branchesService;

    // 지점 목록 페이지
    @GetMapping({"/", ""})
    public String listBranches(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        List<BranchesDTO> branches;
        if (keyword == null || keyword.isEmpty()) {
            branches = branchesService.getAllBranches();
        } else {
            branches = branchesService.searchBranches(keyword);
        }
        model.addAttribute("branchList", branches);
        return "managingSys/branches/branchesManagement";
    }

    
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("branches", new BranchesDTO());
        return "managingSys/branches/branchesRegister";
    }

    @PostMapping("/register")
    public String registerBranches(@ModelAttribute BranchesDTO branchesDTO) {
        branchesService.registerBranch(branchesDTO);
        return "redirect:/managingSys/branches/";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model) {
        BranchesDTO branches = branchesService.getBranchById(id);
        model.addAttribute("branches", branches);
        return "managingSys/branches/branchesEdit";
    }

    @PostMapping("/update")
    public String updateBranches(@ModelAttribute BranchesDTO branchesDTO) {
        System.out.println("update 진입" + branchesDTO.toString());
    	branchesService.updateBranch(branchesDTO.getBranchId(), branchesDTO);
        return "redirect:/managingSys/branches/";
    }

    @PostMapping("/toggle-status/{id}")
    public String toggleBranchStatus(@PathVariable("id") Integer id, 
    											@ModelAttribute BranchesDTO branchesDTO) {
            branchesService.toggleBranchStatus(id, branchesDTO);
            return "redirect:/managingSys/branches/";
    }

    
    @PostMapping("/delete/{id}")
    public ResponseEntity<?> deleteBranches(@PathVariable("id") Integer id) {
        try {
            branchesService.deleteBranch(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error deleting branch: " + e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<BranchesDTO>> getAllBranches() {
        List<BranchesDTO> branches = branchesService.listAllBranches();
        return ResponseEntity.ok(branches);
    }
}