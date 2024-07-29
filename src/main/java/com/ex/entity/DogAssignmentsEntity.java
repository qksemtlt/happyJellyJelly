package com.ex.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "DOGASSIGNMENTS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DogAssignmentsEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DOGASSIGN_SEQ")							
	@SequenceGenerator(name = "DOGASSIGN_SEQ", sequenceName = "DOGASSIGN_SEQ", allocationSize=1, initialValue = 1)
	private Integer dogassignment_id;
	
	@ManyToOne
	private DogsEntity dogs;
	
	private Integer daygroup_id;
	
	private Integer monthgroup_id; 
	
	private LocalDate start_date;
    private LocalDate end_date;
}





