package com.ex.data;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BranchListResponseDTO {
    private List<BranchDTO> branches;
    private long totalElements;
    private int totalPages;
}

