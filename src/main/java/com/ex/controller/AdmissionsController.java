package com.ex.controller;


import com.ex.data.AdmissionsDTO;
import com.ex.service.AdmissionsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admissions")
@RequiredArgsConstructor
public class AdmissionsController {
        
        private final AdmissionsService admissionsService;
        
        @GetMapping("")
        public String admissions() {
                return "admissions/admissions";
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
        
       
        
   
