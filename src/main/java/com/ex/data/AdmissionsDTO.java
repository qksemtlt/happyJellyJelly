package com.ex.data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdmissionsDTO {
		
	private Long damission_id;
	private Long dog_id;
	private LocalDateTime application_date;
	private String status;
	private LocalDateTime approval_date;
	private String desired_subs_type;
	private Long desired_usage_count;
	private Long desired_day_per_week;
}





