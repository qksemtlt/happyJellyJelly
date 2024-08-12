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
@Table(name = "STAFFASSIGNMENTS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StaffAssignmentsEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STAFFASSIGN_SEQ")							
	@SequenceGenerator(name = "STAFFASSIGN_SEQ", sequenceName = "STAFFASSIGN_SEQ", allocationSize=1, initialValue = 1)
	private Integer sfassignment_id;
	
	@ManyToOne
	private MembersEntity members;	
	
	private Integer	daygroup_id;
	
	private Integer	monthgroup_id;
	
	private LocalDate start_date;
	
	private LocalDate end_date;

	

}
