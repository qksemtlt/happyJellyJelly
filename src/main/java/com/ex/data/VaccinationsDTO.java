package com.ex.data;

import java.time.LocalDate;

import com.ex.entity.AdmissionsEntity;
import com.ex.entity.DogsEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class VaccinationsDTO {
	private Integer vaccination_id ;
	private DogsEntity dogs;
	private String	vaccine_type;
	private LocalDate	vaccination_date ;
	private LocalDate	expiry_date ;
	private String	filename;
	private AdmissionsEntity  admissions;
	
}
