package com.ex.data;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BranchDTO {
    private Integer branch_id;
    private String name;
    private String address;
    private String phone;
    private Integer manager_id;
    private boolean active;
    
    private List<BranchDTO> branches;
    private int totalPages;
    private long totalElements;
}