package com.ex.controller;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.ex.data.VaccinationsDTO;
import com.ex.entity.DogsEntity;
import com.ex.entity.MembersEntity;
import com.ex.service.VaccinationsService;
import com.ex.service.DogService;
import com.ex.service.MembersService;
import lombok.RequiredArgsConstructor;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/vaccinations")
@RequiredArgsConstructor
public class VaccinationsController {
    private final VaccinationsService vaccinationsService;
    private final DogService dogsService;
    private final MembersService membersService;

    @GetMapping("/vaccinationsForm")
    @PreAuthorize("isAuthenticated()")
    public String showVaccinationForm(Model model, Principal principal) {
        String username = principal.getName();
        MembersEntity member = membersService.findByUsername(username);

        List<DogsEntity> userDogs = dogsService.findDogsByMember(member);

        model.addAttribute("userDogs", userDogs);
        model.addAttribute("vaccinationsDTO", new VaccinationsDTO());

        return "vaccinations/vaccinationsForm";
    }

    @PostMapping("/save")  // 백신 정보 저장 요청 처리
    @PreAuthorize("isAuthenticated()")  // 로그인한 사용자만 접근 가능
    public String saveVaccination( @RequestParam(value = "vaccineType", required = false) List<String> vaccineTypes,  // 선택된 신 백타입들을 리스트로 받음 (필수 아님)    
                                   @ModelAttribute VaccinationsDTO vaccinationsDTO,  // 폼에서 입력된 백신 접종 정보
                                   @RequestParam("file") MultipartFile file  /* 업로드된 파일*/ ) {
    	StringBuilder combinedVaccineTypes = new StringBuilder();

    	for(int i = 0; i < vaccineTypes.size(); i++) {
    	    System.out.println("================================="+vaccineTypes.get(i));
    	    combinedVaccineTypes.append(vaccineTypes.get(i));
    	    if (i < vaccineTypes.size() - 1) {
    	        combinedVaccineTypes.append(",");  // 마지막 요소가 아니면 쉼표 추가
    	        
    	        System.out.println("=============================="+combinedVaccineTypes);
    	    }
    	}

    	vaccinationsDTO.setVaccineType(combinedVaccineTypes.toString());
    	vaccinationsService.saveVaccinationWithFile(vaccinationsDTO, file);
       /*
        if (vaccineTypes != null && !vaccineTypes.isEmpty()) {  
            // 선택된 백신 타입이 있으면
            combinedVaccineTypes = String.join(",", vaccineTypes);  
            // 백신 타입들을 쉼표로 구분해서 하나의 문자열로 만듦
        }
        vaccinationsDTO.setVaccineType(combinedVaccineTypes);  
        // 합친 백신 타입 문자열을 DTO에 저장
        vaccinationsService.saveVaccinationWithFile(vaccinationsDTO, file);  
        // 백신 정보와 파일을 저장하는 서비스 호출
	*/
        return "redirect:/";  // 메인 페이지로 이동
    }
}