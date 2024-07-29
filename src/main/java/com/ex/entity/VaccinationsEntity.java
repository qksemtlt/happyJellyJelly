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
@Table(name = "VACCINATIONS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VaccinationsEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "VACCINATIONS _SEQ")							
	@SequenceGenerator(name = "VACCINATIONS _SEQ", sequenceName = "VACCINATIONS _SEQ", allocationSize=1, initialValue = 1)
	private Integer vaccination_id ;
	
	@ManyToOne
	private DogsEntity dogs;
	
	private String	vaccine_type;
	
	private LocalDate vaccination_date ;
	
	private LocalDate expiry_date ;
	
	private String filename;
	@ManyToOne
	private AdmissionsEntity  admissions;
	
}
