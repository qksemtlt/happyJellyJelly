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
        
       
        
       
        
   
}