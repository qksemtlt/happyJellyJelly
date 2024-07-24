package com.ex.data;

import java.time.LocalDate;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StaffDetailDTO {	
	private Integer staffdetail_id ;
	private Integer member_id;
	private String  position;
	private LocalDate hire_date;
	private Integer branch_id;
}

