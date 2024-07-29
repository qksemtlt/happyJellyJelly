package com.ex.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "DAILYREPORTS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DailyReportsEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "REPORT _SEQ")							
	@SequenceGenerator(name = "REPORT _SEQ", sequenceName = "REPORT _SEQ", allocationSize=1, initialValue = 1)
	private Integer report_id ;
	
	@ManyToOne
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
