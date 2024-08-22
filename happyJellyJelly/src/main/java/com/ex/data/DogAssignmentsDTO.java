package com.ex.data;
import java.time.LocalDate;

import com.ex.entity.AdmissionsEntity;
import com.ex.entity.DogsEntity;
import com.ex.entity.MonthcareGroupsEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DogAssignmentsDTO {
	 private Integer dogassignmentId;
	 private DogsEntity dogs;
	 private Integer daygroup_id;
	 private MonthcareGroupsEntity monthgroup;
	 private LocalDate startDate;
	 private LocalDate endDate;
	 private AdmissionsEntity admission;

}