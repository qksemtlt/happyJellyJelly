package com.ex.controller;

import com.ex.data.BranchDTO;
import com.ex.data.BranchListResponseDTO;
import com.ex.service.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/branch")
public class BranchController {

    @Autowired
    private BranchService branchService;

    @GetMapping({"", "/"})
    public String listBranches(Model model) {
        BranchListResponseDTO branchList = branchService.listAllBranches();
        System.out.println("Branch list size: " + (branchList != null ? branchList.getBranches().size() : "null"));
        model.addAttribute("branchList", branchList);
        return "branch/branchList";
    }
    
//생성
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("branch", new BranchDTO());
        return "branch/branchRegister";
    }

    @PostMapping("/register")
    public String registerBranch(@ModelAttribute BranchDTO branchDTO) {
        branchService.registerBranch(branchDTO);
        return "redirect:/branch";  // "/branch/list" 대신 "/branch"로 변경
    }
//수정
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model) {
        BranchDTO branch = branchService.getBranchById(id);
        model.addAttribute("branch", branch);
        return "branch/branchEdit";
    }

    @PostMapping("/update")
    public String updateBranch(@ModelAttribute BranchDTO branchDTO) {
        branchService.updateBranch(branchDTO);
        return "redirect:/branch/";
    }
//활성/비활성
    @PutMapping("/toggle-status/{id}")
    public ResponseEntity<?> toggleBranchStatus(@PathVariable("id") Integer id) {
        try {
            BranchDTO updatedBranch = branchService.toggleBranchStatus(id);
            return ResponseEntity.ok(updatedBranch);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error toggling branch status");
        }
    }
//삭제
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBranch(@PathVariable("id") Integer id) {
        try {
            branchService.deleteBranch(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting branch");
        }
    }
    
}		
    
    
    
    
