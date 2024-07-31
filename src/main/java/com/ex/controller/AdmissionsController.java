package com.ex.controller;


import com.ex.data.AdmissionsDTO;
import com.ex.entity.DogsEntity;
import com.ex.entity.MembersEntity;
import com.ex.service.AdmissionsService;
import com.ex.service.DogService;
import com.ex.service.MembersService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admissions")
@RequiredArgsConstructor
public class AdmissionsController {
        
        private final AdmissionsService admissionsService;
        private final DogService dogService;      
        private final MembersService membersService;
            
        // 추가
        @GetMapping("")
        public String AdmissionForm(Model model, Principal principal) {
            // 현재 로그인한 사용자 정보 가져오기
            String username = principal.getName();
            MembersEntity member = membersService.findByUsername(username);
            // 사용자의 강아지 정보 가져오기
            List<DogsEntity> userDogs = dogService.findDogsByMember(member);
            // 모델에 강아지 정보 추가
            model.addAttribute("userDogs", userDogs);
            return "admissions/admissions";
        }  
        
        @PostMapping("/create")
        public String createAdmission(@ModelAttribute AdmissionsDTO admissionDTO, RedirectAttributes redirectAttributes) {
            try {
                admissionsService.createAdmission(admissionDTO);
                redirectAttributes.addFlashAttribute("message", "입학 신청이 성공적으로 제출되었습니다.");
                return "redirect:/admissions/admissionsList";
            } catch (RuntimeException e) {
                redirectAttributes.addFlashAttribute("error", "입학 신청 중 오류가 발생했습니다: " + e.getMessage());
                return "redirect:/admissions";
            }
        }
        
        @GetMapping("/admissionsList")
        public String admissionsList(Model model) {
            List<AdmissionsDTO> admissions = admissionsService.getAllAdmissions();
            model.addAttribute("admissions", admissions);
            return "admissions/admissionsList";
        }
        
        @GetMapping("/admissionsDetail/{id}")
        public String viewAdmission(@PathVariable("id") Integer id, Model model) {
            AdmissionsDTO admission = admissionsService.getAdmissionById(id);
            model.addAttribute("admission", admission);
            return "admissions/admissionsDetail";
        }
        
        @PostMapping("/updateStatus/{id}")
        public String updateAdmissionStatus(
                @PathVariable("id") Integer id, 
                @RequestParam("status") String status) {
            AdmissionsDTO updatedAdmission = admissionsService.updateAdmissionStatus(id, status);
            return "redirect:/admissions/admissionsList";
        }

    }
        
       
        
   
