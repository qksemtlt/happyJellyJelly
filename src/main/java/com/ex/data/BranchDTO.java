package com.ex.data;
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
    private Integer managerId;  // Integer 타입으로 정의
    private String managerName;
    private boolean active;
    private int totalPages;
    private long totalElements;
}