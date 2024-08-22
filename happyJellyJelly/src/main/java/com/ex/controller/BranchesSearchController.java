package com.ex.controller;
import com.ex.data.BranchesDTO;
import com.ex.data.BranchesListResponseDTO;
import com.ex.service.BranchesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/searchMap")
public class BranchesSearchController {
    private final BranchesService branchesService;

    @GetMapping("/branchSearchMain")
    public String branchSearchMain() {
        return "searchMap/branchSearchMain";
    }

    @GetMapping("/api/branches")
    @ResponseBody
    public ResponseEntity<BranchesListResponseDTO> getAllBranches(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam(defaultValue = "false") boolean activeOnly) {
        BranchesListResponseDTO response = branchesService.listBranches(page, size, sortBy, sortDir, activeOnly);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    @ResponseBody
    public ResponseEntity<BranchesListResponseDTO> searchBranches(
            @RequestParam String term,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam(defaultValue = "false") boolean activeOnly) {
        
        BranchesListResponseDTO response = branchesService.searchBranches(term, page, size, sortBy, sortDir, activeOnly);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/branches/map")
    @ResponseBody
    public ResponseEntity<List<BranchesDTO>> getBranchesForMap() {
        List<BranchesDTO> branches = branchesService.getAllActiveBranches();
        return ResponseEntity.ok(branches);
    }

}