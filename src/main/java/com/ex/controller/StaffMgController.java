package com.ex.controller;

import com.ex.data.BranchesDTO;
import com.ex.data.StaffMgDTO;
import com.ex.service.StaffMgService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/managingSys/staff/*")
public class StaffMgController {

    private final StaffMgService staffMgService;

 // 직원 목록을 조회하고 staffManagement 페이지를 반환하는 메서드
    @GetMapping("")
    public String listStaff(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        List<StaffMgDTO> staffList;
//        if (keyword == null || keyword.equals("")) {
//            staffList = staffMgService.getAllStaff(); // 전체 직원 조회
//        } else {
//            staffList = staffMgService.searchStaff(keyword); // 키워드로 직원 검색
//        }
        staffList = staffMgService.getAllStaff();
        System.out.println("Staff list size: " + staffList.size()); // 로그 추가
        model.addAttribute("staffList", staffList);
        return "managingSys/staffDetail/staffManagement";
    }
    
    // 특정 ID의 직원 정보를 JSON 형태로 반환하는 메서드
    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<StaffMgDTO> getStaff(@PathVariable Integer id) {
        StaffMgDTO staff = staffMgService.getStaffById(id);
        return ResponseEntity.ok(staff);
    }
    
    // 직원 등록 폼을 보여주는 메서드
    @GetMapping("/register")
    public String showRegisterStaffForm(Model model) {
    	List<StaffMgDTO> staffList = staffMgService.getAllRegularMembers();
    	System.out.println("=========================="+staffList);
        model.addAttribute("memberList", staffMgService.getAllRegularMembers());
        model.addAttribute("branchList", staffMgService.getAllBranches());
        return "managingSys/staffDetail/staffRegister";
    }

    // 새 직원을 등록하는 메서드
    @PostMapping("/register")
    public String registerStaff( StaffMgDTO staffDTO) {
        try {
            staffMgService.registerStaff(staffDTO);
            return "managingSys/staffDetail/staffRegisterSuccss";
        } catch (Exception e) {
            e.printStackTrace();
            return "x";
        }
    }
    
    @GetMapping("/staffEdit/{id}")
    public String showEditStaffForm(@PathVariable("id") Integer id, Model model) {
        StaffMgDTO staff = staffMgService.getStaffById(id);
        List<BranchesDTO> branches = staffMgService.getAllBranches();
        model.addAttribute("staff", staff);
        model.addAttribute("branches", branches);
        return "managingSys/staffDetail/staffEdit";
    }

    @PostMapping("/staffEdit/{id}")
    public String updateStaff(@PathVariable("id") Integer id, @ModelAttribute StaffMgDTO staffDTO, RedirectAttributes redirectAttributes) {
        try {
            StaffMgDTO updatedStaff = staffMgService.updateStaff(id, staffDTO);
            redirectAttributes.addFlashAttribute("successMessage", "직원 정보가 성공적으로 수정되었습니다.");
            if (!updatedStaff.isActive()) {
                redirectAttributes.addFlashAttribute("infoMessage", "직원이 퇴사 처리되었습니다.");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "직원 정보 수정 중 오류가 발생했습니다: " + e.getMessage());
        }
        return "redirect:/managingSys/staffDetail";
    }
    
    // 직원의 타입(직급)을 업데이트하는 메서드
    @PutMapping("/staffEdit/{id}")
    @ResponseBody
    public ResponseEntity<StaffMgDTO> updateStaffType(@PathVariable Integer id, @RequestParam String newType) {
        StaffMgDTO updatedStaff = staffMgService.updateStaffType(id, newType);
        return ResponseEntity.ok(updatedStaff);
    }

    // 직원을 비활성화(퇴사 처리)하는 메서드
    @PutMapping("/{id}/deactivate")
    @ResponseBody
    public ResponseEntity<Boolean> deactivateStaff(@PathVariable Integer id) {
        boolean deactivated = staffMgService.deactivateStaff(id);
        return ResponseEntity.ok(deactivated);
    }

    // 직원 정보를 삭제하는 메서드
    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public ResponseEntity<Boolean> deleteStaff(@PathVariable Integer id) {
        boolean deleted = staffMgService.deleteStaff(id);
        return ResponseEntity.ok(deleted);
    }
}