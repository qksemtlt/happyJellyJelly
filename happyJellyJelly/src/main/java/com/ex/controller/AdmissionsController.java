package com.ex.controller;
import com.ex.data.AdmissionsDTO;
import com.ex.data.DogsDTO;
import com.ex.data.MonthcareGroupsDTO;
import com.ex.data.TicketDTO;
import com.ex.entity.BranchEntity;
import com.ex.entity.DogsEntity;
import com.ex.entity.MembersEntity;
import com.ex.entity.VaccinationsEntity;
import com.ex.service.AdmissionsService;
import com.ex.service.DogService;
import com.ex.service.MembersService;
import com.ex.service.TicketService;
import com.ex.service.VaccinationsService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
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
        private final TicketService ticketService;
            
        @GetMapping("")
        @PreAuthorize("isAuthenticated()")
        public String AdmissionForm(Model model, Principal principal) {
            // 현재 로그인한 사용자 정보 가져오기
            String username = principal.getName();
            MembersEntity member = membersService.findByUsername(username);

            // 사용자의 강아지 정보 가져오기
            List<DogsEntity> userDogs = dogService.findDogsByMember(member);
            
            // 지점 목록 가져오기
            List<BranchEntity> branches = admissionsService.getAllBranches();
            model.addAttribute("branches", branches);
            
            List<DogsEntity> dogsWithoutPendingAdmissions = new ArrayList<>();
            Map<Integer, List<MonthcareGroupsDTO>> branchGroups = new HashMap<>();
            for (BranchEntity branch : branches) {
                List<MonthcareGroupsDTO> groups = admissionsService.getGroupsByBranch(branch.getBranchId());
                branchGroups.put(branch.getBranchId(), groups);
            }
            
            for (DogsEntity dog : userDogs) {
                boolean hasPendingAdmission = dog.getAdmissions().stream()
                        .anyMatch(admission -> admission.getStatus().equals("PENDING"));
                if (!hasPendingAdmission) {
                    dogsWithoutPendingAdmissions.add(dog);
                }
            }

            // 모델에 정보 추가
            model.addAttribute("userDogs", dogsWithoutPendingAdmissions);
            model.addAttribute("admissionDTO", new AdmissionsDTO());
            model.addAttribute("branchGroups", branchGroups);
            return "admissions/admissions";
        }
        
        
        @PostMapping("/create")
        @PreAuthorize("isAuthenticated()")
        public String createAdmission(@ModelAttribute AdmissionsDTO admissionDTO, RedirectAttributes redirectAttributes) {
            try {
                admissionsService.createAdmission(admissionDTO);
                redirectAttributes.addFlashAttribute("successMessage", "입학 신청이 성공적으로 완료되었습니다.");
                return "redirect:/admissions/admissionsList";
                
            } catch (RuntimeException e) {
                redirectAttributes.addFlashAttribute("errorMessage", "입학 신청 중 오류가 발생했습니다: " + e.getMessage());
                return "redirect:/admissions";
            }
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
               List<BranchEntity> branches = admissionsService.getAllBranches();
               model.addAttribute("branches", branches);
               Map<Integer, List<MonthcareGroupsDTO>> branchGroups = new HashMap<>();
               for (BranchEntity branch : branches) {
                   List<MonthcareGroupsDTO> groups = admissionsService.getGroupsByBranch(branch.getBranchId());
                   branchGroups.put(branch.getBranchId(), groups);
               }
               model.addAttribute("branchGroups", branchGroups);
               return "admissions/admissions";              
           }           
        }
                
       
        @GetMapping("/admissionsList")
        @PreAuthorize("isAuthenticated()")
        public String admissionsList(Model model, Principal principal,
                                     @RequestParam(value = "page", defaultValue = "0") int page,
                                     @RequestParam(value = "status", required = false) String status) {

            String username = principal.getName();
            MembersEntity member = membersService.findByUsername(username);

            Page<AdmissionsDTO> paginatedAdmissions = admissionsService.getAdmissionsByRole(username, page, status);

            model.addAttribute("userType", member.getUserType());
            model.addAttribute("admissionsList", paginatedAdmissions);
            model.addAttribute("currentStatus", status);

            if (!("ADMIN".equals(member.getUserType()) || "DIRECTOR".equals(member.getUserType()))) {
                List<DogsDTO> dogsList = dogService.myDogList(username);
                model.addAttribute("dogsList", dogsList);
            }

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
        
        
        @GetMapping("/cancel/{id}")
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
        
        
        @GetMapping("/notice")
        public String admissionNotice(Model model) {
        	List<TicketDTO> ticketDTO = ticketService.getTicketsList();
        	model.addAttribute("ticketDTO", ticketDTO);
            return "/admissions/admission_notice";
        }
}