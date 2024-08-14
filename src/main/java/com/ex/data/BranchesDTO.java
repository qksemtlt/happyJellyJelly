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
public class BranchesDTO {
	private Integer branchId;
	private String branchesName;
	private String address;
	private String phone;
	private boolean active;
	private List<MonthcareGroupsDTO> monthcareGroups;

	private List<BranchesDTO> branches;
	private int totalPages;
	private long totalElements;

	private Double latitude;
	private Double longitude;
}
