package com.ex.data;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BranchesListResponseDTO {
    private List<BranchesDTO> branches;
    private long totalElements;
    private int totalPages;
}
