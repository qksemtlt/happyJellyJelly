package com.ex.controller;


import com.ex.data.AdmissionsDTO;
import com.ex.data.BranchesDTO;
import com.ex.data.DogsDTO;
import com.ex.data.MonthcareGroupsDTO;
import com.ex.data.VaccinationsDTO;
import com.ex.entity.AdmissionsEntity;
import com.ex.entity.DogsEntity;
import com.ex.entity.MembersEntity;
import com.ex.entity.VaccinationsEntity;
import com.ex.service.AdmissionsService;
import com.ex.service.BranchesService;
import com.ex.service.DogService;
import com.ex.service.MembersService;
import com.ex.service.MonthcareGroupsService;
import com.ex.service.VaccinationsService;

import lombok.RequiredArgsConstructor;

import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admissions")
@RequiredArgsConstructor
public class AdmissionsController {
        
        private final AdmissionsService admissionsService;
        private final DogService dogService;      
        private final MembersService membersService;
        private final VaccinationsService vaccinationsService;
        private final BranchesService branchesService;
        private final MonthcareGroupsService monthcareGroupsService;
            
        // 
        @GetMapping("")
        @PreAuthorize("isAuthenticated()")
        public String AdmissionForm(Model model, Principal principal,
        							@RequestParam(name = "branchId", required = false) Integer branchId) {
            // 현재 로그인한 사용자 정보 가져오기
            String username = principal.getName();
            MembersEntity member = membersService.findByUsername(username);

            // 사용자의 강아지 정보 가져오기
            List<DogsEntity> userDogs = dogService.findDogsByMember(member);
            
            List<DogsEntity> dogsWithoutPendingAdmissions = new ArrayList<>();
           
            for (DogsEntity dog : userDogs) {	// 사용자의 강아지 뽑아오기
                boolean hasPendingAdmission = false;	// 기본값 설정
                for (AdmissionsEntity admission : dog.getAdmissions()) { // 사용자의 강아지 한마리씩 어드미션 신청서 조회
                    if (admission.getStatus().equals("PENDING")) {	// 스테이터스가 대기중이면 
                        hasPendingAdmission = true;						// 트류로 바뀜
                        break;										// 대기중이면 멈춘다 
                    }
                }
                if (!hasPendingAdmission) {		// 값이 대기중이 아니면 
                    dogsWithoutPendingAdmissions.add(dog); // 저장 시킴?
                }
            }
            List<BranchesDTO> branches = branchesService.getAllBranches();
            model.addAttribute("branches", branches);
            
            Map<Integer, List<MonthcareGroupsDTO>> branchGroups = new HashMap<>();
            for (BranchesDTO branch : branches) {
                List<MonthcareGroupsDTO> groups = monthcareGroupsService.getMonthcareGroupByBranch(branch.getBranchId());
                branchGroups.put(branch.getBranchId(), groups);
            }
            model.addAttribute("userDogs", branchGroups);

            // 모델에 정보 추가
            model.addAttribute("userDogs", dogsWithoutPendingAdmissions);
            return "admissions/admissions";
        }
        
        
        
        @GetMapping("/{id}")
        @PreAuthorize("isAuthenticated()")
        public String AdmissionForm(@PathVariable("id") Integer id, 
              Principal principal, Model model, RedirectAttributes redirectAttributes) {
           String status = "PENDING";
           int checkPending = admissionsService.checkPending(status, id);
           if(checkPending >= 1) {
        	   redirectAttributes.addFlashAttribute("message", "입학 승인 대기 중입니다.");
        	   return String.format("redirect:/dogs/detail/%s", id);
           }else {
        	   DogsDTO dogsDTO = dogService.selectDog(id, principal.getName());
               model.addAttribute("dog", dogsDTO);
               model.addAttribute("dog_id", id);
               return "admissions/admissions";
           }         
        }
        
        
        
        @PostMapping("/create")
        @PreAuthorize("isAuthenticated()")
        public String createAdmission(@ModelAttribute AdmissionsDTO admissionDTO) {
            try {
                admissionsService.createAdmission(admissionDTO);
               
                return "redirect:/admissions/admissionsList";
            } catch (RuntimeException e) {
               
                return "redirect:/admissionsList";
            }
        }
        
        @GetMapping("/admissionsList")
        @PreAuthorize("isAuthenticated()")
        public String admissionsList(Model model, Principal principal,
                                     @RequestParam(value = "page", defaultValue = "0") int page) {
            String username = principal.getName();
            Page<AdmissionsDTO> paginatedAdmissions;

            if (username.startsWith("director_")) {
                // 관리자: 모든 입학신청서를 가져옴
                paginatedAdmissions = admissionsService.getAllAdmissionsPaginated(page);
                model.addAttribute("isDirector", true);
            } else {
                // 일반 사용자: 해당 사용자의 강아지 입학신청서만 가져옴
                paginatedAdmissions = admissionsService.getAdmissionsByUsernamePaginated(username, page);
                model.addAttribute("isDirector", false);
            }

            model.addAttribute("admissionsList", paginatedAdmissions);
            return "admissions/admissionsList";
        }
        
        
        
        @GetMapping("/admissionsDetail/{id}")
        @PreAuthorize("isAuthenticated()")
        public String viewAdmission(@PathVariable("id") Integer id, Model model) {
            AdmissionsDTO admission = admissionsService.getAdmissionById(id);
            model.addAttribute("admission", admission);
            
            // 해당 강아지의 백신 정보 가져오기
            if (admission != null && admission.getDogs() != null) {
                List<VaccinationsEntity> vaccinations = vaccinationsService.getVaccinationsByDogId(admission.getDogs().getDogId());
                model.addAttribute("vaccinations", vaccinations);
            }
            
            return "admissions/admissionsDetail";
     
        
        }
        @PostMapping("/updateStatus/{id}")
        @PreAuthorize("isAuthenticated()")
        public String updateAdmissionStatus(
                @PathVariable("id") Integer id, 
                @RequestParam("status") String status, @RequestParam("reason") String reason) {
            admissionsService.updateAdmissionStatus(id, status, reason);
            return "redirect:/admissions/admissionsList";
        }
        
        @PostMapping("/cancel/{id}")
        @PreAuthorize("isAuthenticated()")
        public String cancelAdmission(@PathVariable("id") Integer id) {
           admissionsService.cancelAdmission(id);
           return "redirect:/admissions/admissionsList";
        }
        @GetMapping("/vaccinations/file/{filename:.+}")
        @ResponseBody
        public ResponseEntity<Resource> serveFile(@PathVariable("filename")  String filename) {
            Resource file = vaccinationsService.loadFileAsResource(filename);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + file.getFilename() + "\"")
                    .body(file);
        }
    
        
    
    }
        
       
        
   
