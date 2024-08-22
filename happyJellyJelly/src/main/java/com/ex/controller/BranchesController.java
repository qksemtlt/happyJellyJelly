package com.ex.controller;
import com.ex.data.BranchesDTO;
import com.ex.service.BranchesService;
import com.ex.service.KakaoApiService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/managingSys/branches")
public class BranchesController {
    private final BranchesService branchesService;
    private final KakaoApiService kakaoApiService;
    private static final Logger logger = LoggerFactory.getLogger(BranchesController.class);

    // 지점 등록 폼 페이지
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("branchesDTO", new BranchesDTO());
        return "managingSys/branches/branchesRegister";
    }
    
    // 지점 등록 처리
    @PostMapping("/register")
    public String registerBranch(@ModelAttribute BranchesDTO branchesDTO, RedirectAttributes redirectAttributes) {
        try {
            BranchesDTO registeredBranch = branchesService.registerBranch(branchesDTO);
            redirectAttributes.addFlashAttribute("successMessage", "매장이 성공적으로 등록되었습니다.");
            return "redirect:/managingSys/branches";
        } catch (Exception e) {
            logger.error("Error registering branch: {}", e.getMessage(), e);
            redirectAttributes.addFlashAttribute("errorMessage", "매장 등록 중 오류가 발생했습니다: " + e.getMessage());
            return "redirect:/managingSys/branches/register";
        }
    }

    // 주소 좌표 변환 API
    @PostMapping("/geocode")
    public ResponseEntity<?> getGeocode(@RequestParam("postCode") String postCode,
                                        @RequestParam("address") String address,
                                        @RequestParam("address2") String address2) {
        try {
            Map<String, Double> coordinates = kakaoApiService.getCoordinatesFromAddress(postCode, address, address2);
            return ResponseEntity.ok(coordinates);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while geocoding");
        }
    }

    // 지점 수정 폼 페이지
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model) {
        BranchesDTO branches = branchesService.getBranchById(id);
        model.addAttribute("branches", branches);
        return "managingSys/branches/branchesEdit";
    }

    // 지점 정보 업데이트 처리
    @PostMapping("/update")
    public String updateBranches(@ModelAttribute BranchesDTO branchesDTO) {
        branchesService.updateBranch(branchesDTO.getBranchId(), branchesDTO);
        return "redirect:/managingSys/branches";
    }

    // 지점 상태 토글 (활성/비활성)
    @PostMapping("/toggle-status/{id}")
    public String toggleBranchStatus(@PathVariable("id") Integer id, @ModelAttribute BranchesDTO branchesDTO) {
        branchesService.toggleBranchStatus(id);
        return "redirect:/managingSys/branches";
    }

    // 지점 삭제 처리
    @PostMapping("/delete/{id}")
    public String deleteBranches(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        try {
            branchesService.deleteBranch(id);
            redirectAttributes.addFlashAttribute("successMessage", "매장이 성공적으로 삭제되었습니다.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "매장 삭제에 실패했습니다: " + e.getMessage());
        }
        return "redirect:/managingSys/branches";
    }
}