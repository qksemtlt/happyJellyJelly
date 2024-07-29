package com.ex.data;

import java.time.LocalDate;

import com.ex.entity.DogsEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class DailyReportsDTO {
	private Integer report_id ;
	private DogsEntity	dogs ;
	private Integer	attendance_id ;
	private LocalDate	report_date;
	private String	behavior;
	private String	activities;
	private String	meals;
	private String	health;
	private String	bowel;
	private String contents;
}
