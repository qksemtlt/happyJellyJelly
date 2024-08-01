package com.ex.controller;


import com.ex.data.AdmissionsDTO;
import com.ex.data.DogsDTO;
import com.ex.entity.DogsEntity;
import com.ex.entity.MembersEntity;
import com.ex.service.AdmissionsService;
import com.ex.service.DogService;
import com.ex.service.MembersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admissions")
@RequiredArgsConstructor
public class AdmissionsController {
        
        private final AdmissionsService admissionsService;
        private final DogService dogService;      
        private final MembersService membersService;
            
        // 
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
        
        
        @GetMapping("/{id}")
        public String AdmissionForm(@PathVariable("id") Integer id, 
        		Principal principal, Model model) {
        	DogsDTO dogsDTO = dogService.selectDog(id, principal.getName());
        	model.addAttribute("dog", dogsDTO);
        	model.addAttribute("dog_id", id);
        	return "admissions/admissions";
        }
        
        
        
        @PostMapping("/create")
        public String createAdmission(@ModelAttribute AdmissionsDTO admissionDTO) {
            try {
                admissionsService.createAdmission(admissionDTO);
               
                return "redirect:/admissions/admissionsList";
            } catch (RuntimeException e) {
               
                return "redirect:/admissionsList";
            }
        }
        
        @GetMapping("/admissionsList")
        public String admissionsList(Model model, Principal principal) {
            String username = principal.getName();
            List<AdmissionsDTO> admissions;

            if (username.startsWith("director_")) {
                // 관리자: 모든 입학신청서를 가져옴
                admissions = admissionsService.getAllAdmissions();
                model.addAttribute("isDirector", true);
            } else {
                // 일반 사용자: 해당 사용자의 강아지 입학신청서만 가져옴
                admissions = admissionsService.getAdmissionsByUsername(username);
                model.addAttribute("isDirector", false);
            }

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
            AdmissionsDTO admissionsDTO = admissionsService.updateAdmissionStatus(id, status);
            return "redirect:/admissions/admissionsList";
        }

    }
        
       
        
   
