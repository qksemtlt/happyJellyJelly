package com.ex.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

    	    return "vaccinations/vaccinationsForm";
    	}
    

    @PostMapping("/save")
    public String saveVaccination(VaccinationsDTO vaccinationsDTO) {
        vaccinationsService.saveVaccination(vaccinationsDTO);
        return "redirect:/";
    }
}